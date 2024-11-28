package com.example.orderservice.service;

import com.example.orderservice.dto.request.OrderLineItemDto;
import com.example.orderservice.dto.request.OrderRequest;
import com.example.orderservice.dto.response.InventoryResponse;
import com.example.orderservice.dto.response.ProductResponse;
import com.example.orderservice.event.OrderPlacedEvent;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderLineItems;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepo;
    private final WebClient.Builder webClient;
    private final Tracer tracer;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.binding.noti.order.routing.key}")
    private String routingKey;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToListDto)
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());
//        call inventory service, and place order if product is in
//        stock
        log.info("skuCodes: " + skuCodes);
        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");
        try (Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())) {
            InventoryResponse[] inventoryResponses = webClient.build().get()
                    .uri("http://inventory-server:9003/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

//        log.info("inventoryResponsesArray: "+ Arrays.toString(inventoryResponsesArray));
            boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {

                orderRepo.save(order);
//                kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
//                rabbitTemplate.convertAndSend(exchangeName, routingKey, true); //test truoc
                webClient.build().post().uri("http://product-service:9001/api/product/remove-cache").exchangeToMono(clientResponse -> Mono.empty())
                        .doOnError(e -> log.error("Error occurred during fire-and-forget: ", e))
                        .subscribe();
                return "Order Placed Successfully";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        } finally {
            inventoryServiceLookup.end();
        }


    }

    private OrderLineItems mapToListDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());
        return orderLineItems;
    }
}

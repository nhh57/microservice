package com.example.productservice.dto.response;

import com.example.productservice.model.TicketDetail;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TicketDetailResponse {
    private Long id;
    private String name;
    private String description;
    private int stockInitial;
    private int stockAvailable;
    private boolean isStockPrepared;
    private Long priceOriginal;
    private Long priceFlash;
    private Date saleStartTime;
    private Date saleEndTime;
    private int status;
    private Long activityId;
    private Date updatedAt;
    private Date createdAt;

    public static TicketDetailResponse fromProduct(TicketDetail ticketDetail) {
        return TicketDetailResponse.builder()
                .id(ticketDetail.getId())
                .name(ticketDetail.getName())
                .description(ticketDetail.getDescription())
                .stockInitial(ticketDetail.getStockInitial())
                .stockAvailable(ticketDetail.getStockAvailable())
                .isStockPrepared(ticketDetail.isStockPrepared())
                .priceOriginal(ticketDetail.getPriceOriginal())
                .priceFlash(ticketDetail.getPriceFlash())
                .saleStartTime(ticketDetail.getSaleStartTime())
                .saleEndTime(ticketDetail.getSaleEndTime())
                .status(ticketDetail.getStatus())
                .activityId(ticketDetail.getActivityId())
                .updatedAt(ticketDetail.getUpdatedAt())
                .createdAt(ticketDetail.getCreatedAt())
                .build();
    }


}

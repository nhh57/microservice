package com.example.productservice.repository;

import com.example.productservice.model.TicketDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<TicketDetail,Long> {

    @Query(value = "SELECT t.id, t.name, t.description, t.stock_initial, t.stock_available, t.is_stock_prepared, t.price_original, t.price_flash, t.sale_start_time, t.sale_end_time, t.status, t.activity_id, t.updated_at, t.created_at " +
            "FROM ticket_item t " +
            "WHERE ((?1 <> '' AND (t.name LIKE CONCAT('%', ?1, '%') OR t.description LIKE CONCAT('%', ?1, '%'))) OR ?1 = '')", nativeQuery = true)
    Page<TicketDetail> getAllProducts(String keyword, Pageable pageable);




}

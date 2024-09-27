package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.entities.postgres.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetail, Long> {

    @EntityGraph(attributePaths = {"orderItems.product.productImages.image", "orderItems.productVariant"})
    Optional<OrderDetail> findById(Long id);


    @Query("SELECT od FROM OrderDetail od WHERE od.id = :id")
    Optional<OrderDetail> findOrderDetailBriefById(Long id);


    @Query(value = "SELECT DISTINCT od FROM OrderDetail od " +
            "LEFT JOIN FETCH od.orderItems oi " +
            "LEFT JOIN FETCH oi.product p " +
            "LEFT JOIN FETCH oi.productVariant pv " +
            "LEFT JOIN FETCH p.productImages pi " +
            "LEFT JOIN FETCH pi.image i",
            countQuery = "SELECT COUNT(DISTINCT od) FROM OrderDetail od")
    Page<OrderDetail> filter(Pageable pageable);


}

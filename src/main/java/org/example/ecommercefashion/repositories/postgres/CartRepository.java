package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.entities.postgres.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = {"cartItems"})
    Optional<Cart> findByUserId(Long userId);

    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.cartItems ci " +
            "LEFT JOIN FETCH ci.product p " +
            "LEFT JOIN FETCH p.productVariants pv " +
            "LEFT JOIN FETCH p.category category " +
            "LEFT JOIN FETCH p.productImages pi " +
            "LEFT JOIN FETCH pi.image i " +
            "WHERE c.userId = :userId")
    Optional<Cart> findDetailByUserId(@Param("userId") Long userId);
}

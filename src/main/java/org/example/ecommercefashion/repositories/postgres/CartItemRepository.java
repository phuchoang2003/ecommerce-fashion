package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.entities.postgres.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = {"product.productVariants", "productVariant.product", "cart.cartItems", "cart.user"})
    Optional<CartItem> findById(Long id);
}

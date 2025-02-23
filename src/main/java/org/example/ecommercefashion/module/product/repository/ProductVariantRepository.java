package org.example.ecommercefashion.module.product.repository;

import org.example.ecommercefashion.module.product.entity.ProductVariant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    @EntityGraph(attributePaths = {"product"})
    Optional<ProductVariant> findById(Long id);
}

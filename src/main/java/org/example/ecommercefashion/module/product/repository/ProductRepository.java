package org.example.ecommercefashion.module.product.repository;

import org.example.ecommercefashion.module.product.entity.Product;
import org.example.ecommercefashion.module.product.enums.ProductState;
import org.example.ecommercefashion.module.product.projection.ProductBriefDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"relProductAttributeValues.attributeValue.attribute", "category.subCategories", "productImages.image", "productVariants"})
    Optional<Product> findById(@Param("id") Long id);


    @Query("SELECT p.id AS id, c.name AS categoryName, p.name AS name, p.slug AS slug, " +
            "p.price AS price, p.state AS state, p.quantity AS quantity, pi.image.url AS urls " +
            "FROM Product p " +
            "JOIN ProductImage pi ON p.id = pi.productId " +
            "JOIN p.category c " +
            "WHERE p.deleted = false")
    Page<ProductBriefDTO> filter(@Param("name") String name,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice,
                                 @Param("state") ProductState state,
                                 @Param("categoryName") String categoryName,
                                 Pageable pageable);


}





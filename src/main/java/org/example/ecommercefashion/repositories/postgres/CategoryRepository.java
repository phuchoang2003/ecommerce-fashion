package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.entities.postgres.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT MAX(c.rightValue) FROM Category c")
    Long getMaxValue();


    @Query("Update Category c SET c.leftValue = c.leftValue + 2 WHERE c.leftValue >= :value")
    @Modifying
    void updateLeftValue(@Param("value") long value);

    @Query("Update Category c SET c.rightValue = c.rightValue + 2 WHERE c.rightValue >= :value")
    @Modifying
    void updateRightValue(@Param("value") long value);

    @EntityGraph(attributePaths = {"subCategories"})
    @Query("SELECT c FROM Category c WHERE c.id = :id")
    Optional<Category> findBy(@Param("id") Long id);

    @EntityGraph(attributePaths = {"subCategories"})
    @Query("SELECT c FROM Category c")
    Page<Category> filter(Pageable pageable);


    @Query("UPDATE Category  c SET c.leftValue = c.leftValue - :distance WHERE c.leftValue > :value")
    @Modifying
    void updateLeftValueAfterDelete(@Param("distance") long distance, @Param("value") long value);


    @Query("UPDATE Category  c SET c.rightValue = c.rightValue - :distance WHERE c.rightValue > :value")
    @Modifying
    void updateRightValueAfterDelete(@Param("distance") long distance, @Param("value") long value);

    boolean existsBySlug(String slug);
}

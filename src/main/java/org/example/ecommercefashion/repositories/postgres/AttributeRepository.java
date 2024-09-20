package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.entities.postgres.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @EntityGraph(attributePaths = {"attributeValues"})
    Page<Attribute> findAll(Pageable pageable);

    boolean existsByKeyAttribute(String keyAttribute);

    boolean existsByDisplayKey(String displayKey);


    @EntityGraph(attributePaths = {"attributeValues"})
    @Query("SELECT a FROM Attribute a WHERE a.id = :id")
    Optional<Attribute> findAttributeById(@Param("id") Long id);

    @Query("SELECT a FROM Attribute a " +
            "JOIN a.relCategoryAttributes r " +
            "WHERE r.category.id = :id " +
            "OR a.isMandatory = true")
    Set<Attribute> findAllAttributeByCategoryIdAndIsMandatory(@Param("id") Long categoryId);


    @Query("SELECT a FROM Attribute a JOIN a.attributeValues av WHERE av.id IN :ids")
    Set<Attribute> findByAttributeValuesIdIn(Set<Long> ids);

    Set<Attribute> findByIdIn(Set<Long> ids);
}

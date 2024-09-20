package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.entities.postgres.AttributeValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    Page<AttributeValue> findAll(Pageable pageable);

    boolean existsByValue(String value);


    boolean existsByDisplayValue(String displayValue);
}

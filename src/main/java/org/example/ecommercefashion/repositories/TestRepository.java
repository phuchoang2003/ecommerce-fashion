package org.example.ecommercefashion.repositories;

import org.example.ecommercefashion.entities.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

  @Query("SELECT t FROM TestEntity t")
  Page<TestEntity> findAllTest(Pageable pageable);
}

package org.example.ecommercefashion.common.storage.repository;

import org.example.ecommercefashion.common.storage.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUrl(String url);
}

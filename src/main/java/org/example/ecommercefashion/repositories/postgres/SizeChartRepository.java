package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.dtos.projection.SizeChartDto;
import org.example.ecommercefashion.entities.postgres.SizeChart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface SizeChartRepository extends JpaRepository<SizeChart, Long> {

    Set<SizeChart> findByIdIn(@Param("ids") Set<Long> ids);


    @Query("SELECT sc.id AS id, " +
            "sc.name AS name, " +
            "sc.description AS description," +
            "i.id AS imageId, " +
            "i.url AS url " +
            "FROM SizeChart sc " +
            "JOIN Image i ON i.id = sc.sizeChartImageId " +
            "WHERE sc.id = :sizeChartId")
    Optional<SizeChartDto> findSizeChartWithImageById(@Param("sizeChartId") Long sizeChartId);


    @Query("SELECT sc.id AS id, " +
            "sc.name AS name, " +
            "sc.description AS description, " +
            "i.id AS imageId, " +
            "i.url AS url " +
            "FROM SizeChart sc " +
            "JOIN Image i ON i.id = sc.sizeChartImageId " +
            "WHERE sc.id IN (:ids)")
    Set<SizeChartDto> findByIds(@Param("ids") List<Long> ids);


    @Query("SELECT sc.id AS id, " +
            "sc.name AS name, " +
            "sc.description AS description," +
            "i.id AS imageId, " +
            "i.url AS url " +
            "FROM SizeChart sc " +
            "JOIN Image i ON i.id = sc.sizeChartImageId")
    Page<SizeChartDto> filter(Pageable pageable);
}

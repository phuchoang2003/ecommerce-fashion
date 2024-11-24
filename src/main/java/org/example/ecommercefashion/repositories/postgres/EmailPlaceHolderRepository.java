package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.dtos.projection.ColumnMetadataProjection;
import org.example.ecommercefashion.entities.postgres.EmailPlaceHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmailPlaceHolderRepository extends JpaRepository<EmailPlaceHolder, Long> {


    @Query(value = "SELECT column_name AS columnName, table_name AS tableName, data_type AS dataType " +
            "FROM information_schema.columns " +
            "WHERE table_schema = 'public' " +
            "  AND table_name != 'email_place_holders' " +
            "  AND (table_name || '_' || column_name) NOT IN " +
            "      (SELECT table_name || '_' || column_name FROM email_place_holders WHERE deleted = false)",
            nativeQuery = true)
    List<ColumnMetadataProjection> findColumnMetadata();

    Set<EmailPlaceHolder> findByPlaceHolderNameIn(Set<String> placeHolderNames);
}

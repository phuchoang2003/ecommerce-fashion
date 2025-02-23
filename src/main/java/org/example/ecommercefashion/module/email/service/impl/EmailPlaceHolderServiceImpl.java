package org.example.ecommercefashion.module.email.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.module.email.projection.ColumnMetadataProjection;
import org.example.ecommercefashion.module.email.entity.EmailPlaceHolder;
import org.example.ecommercefashion.module.email.service.EmailPlaceHolderService;
import org.example.ecommercefashion.module.email.repository.EmailPlaceHolderRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmailPlaceHolderServiceImpl implements EmailPlaceHolderService {

    private static final String ID = "id";
    private static final String UNDERSCORE = "_";

    private final EmailPlaceHolderRepository emailPlaceHolderRepository;


    @Override
    public void createPlaceHolder() {
        emailPlaceHolderRepository.saveAll(convertFromMetadata());
    }


    private List<EmailPlaceHolder> convertFromMetadata() {
        List<ColumnMetadataProjection> metaData = emailPlaceHolderRepository.findColumnMetadata();

        return metaData.stream().map(
                        columnMetadataProjection -> EmailPlaceHolder.builder()
                                .placeHolderName(convertColumnToPlaceHolder(columnMetadataProjection.getColumnName(), columnMetadataProjection.getTableName()))
                                .columnName(columnMetadataProjection.getColumnName())
                                .tableName(columnMetadataProjection.getTableName())
                                .dataType(columnMetadataProjection.getDataType())
                                .build())
                .toList();
    }

    private String convertColumnToPlaceHolder(String column, String table) {
        if (column.equals(ID)) {
            return (table + UNDERSCORE + column).toUpperCase();
        }
        return column.toUpperCase();
    }

}

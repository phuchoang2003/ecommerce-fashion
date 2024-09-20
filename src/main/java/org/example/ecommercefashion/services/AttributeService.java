package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.AttributeRequest;
import org.example.ecommercefashion.dtos.request.UpdateAttributeRequest;
import org.example.ecommercefashion.dtos.response.AttributeResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.Attribute;
import org.springframework.data.domain.Pageable;

public interface AttributeService {

    AttributeResponse createAttribute(AttributeRequest data);

    AttributeResponse getAttributeById(Long id);


    ResponsePage<Attribute, AttributeResponse> filter(Pageable pageable);


    void deleteById(Long id);


    AttributeResponse updateById(Long id, UpdateAttributeRequest data);

    Attribute getAttribute(Long id);

}

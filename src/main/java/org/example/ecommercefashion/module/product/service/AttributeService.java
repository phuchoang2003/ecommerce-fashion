package org.example.ecommercefashion.module.product.service;

import org.example.ecommercefashion.module.product.dto.AttributeRequest;
import org.example.ecommercefashion.module.product.dto.UpdateAttributeRequest;
import org.example.ecommercefashion.module.product.dto.AttributeResponse;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.product.entity.Attribute;
import org.springframework.data.domain.Pageable;

public interface AttributeService {

    AttributeResponse createAttribute(AttributeRequest data);

    AttributeResponse getAttributeById(Long id);


    ResponsePage<Attribute, AttributeResponse> filter(Pageable pageable);


    void deleteById(Long id);


    AttributeResponse updateById(Long id, UpdateAttributeRequest data);

    Attribute getAttribute(Long id);

}

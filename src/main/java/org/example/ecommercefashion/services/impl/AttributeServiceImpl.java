package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import org.example.ecommercefashion.dtos.request.AttributeRequest;
import org.example.ecommercefashion.dtos.request.UpdateAttributeRequest;
import org.example.ecommercefashion.dtos.response.AttributeResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.Attribute;
import org.example.ecommercefashion.entities.postgres.AttributeValue;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.AttributeRepository;
import org.example.ecommercefashion.services.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;


    @Override
    public Attribute getAttribute(Long id) {
        return attributeRepository.findAttributeById(id)
                .orElseThrow(() ->
                        new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.ATTRIBUTE_NOT_FOUND.val()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttributeResponse updateById(Long id, UpdateAttributeRequest data) {
        Attribute attribute = getAttribute(id);
        attribute.setIsMandatory(data.getIsMandatory());
        Set<AttributeValue> attributeValues = data.getNewAttributeValues()
                .stream()
                .map(attributeValueRequest -> AttributeValue.fromRequest(attributeValueRequest, attribute))
                .collect(Collectors.toSet());
        attribute.addAttributeValues(attributeValues);

        return AttributeResponse.fromModel(attribute);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttributeResponse createAttribute(AttributeRequest data) {
        existKey(data.getKey());
        existDisplayKey(data.getDisplayKey());
        Attribute attribute = attributeRepository.save(Attribute.fromRequest(data));
        Set<AttributeValue> attributeValues = data.getAttributeValues()
                .stream()
                .map(attributeValueRequest -> AttributeValue.fromRequest(attributeValueRequest, attribute))
                .collect(Collectors.toSet());
        attribute.addAttributeValues(attributeValues);
        return AttributeResponse.fromModel(attribute);

    }

    private void existKey(String name) {
        if (attributeRepository.existsByKeyAttribute(name)) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.ATTRIBUTE_NAME_DUPLICATE.val());
        }
    }

    private void existDisplayKey(String displayName) {
        if (attributeRepository.existsByDisplayKey(displayName)) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.ATTRIBUTE_DISPLAY_NAME_DUPLICATE.val());
        }
    }

    @Override
    public AttributeResponse getAttributeById(Long id) {
        Attribute attribute = getAttribute(id);
        return AttributeResponse.fromModel(attribute);
    }


    @Override
    public ResponsePage<Attribute, AttributeResponse> filter(Pageable pageable) {
        Page<AttributeResponse> attributes = attributeRepository.findAll(pageable).map(AttributeResponse::fromModel);
        return new ResponsePage<>(attributes);
    }

    @Override
    public void deleteById(Long id) {
        attributeRepository.deleteById(id);
    }
}

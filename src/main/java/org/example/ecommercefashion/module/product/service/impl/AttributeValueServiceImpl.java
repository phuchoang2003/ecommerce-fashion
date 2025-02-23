package org.example.ecommercefashion.module.product.service.impl;

import org.example.ecommercefashion.module.product.repository.AttributeValueRepository;
import org.example.ecommercefashion.module.product.service.AttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {

    @Autowired
    private AttributeValueRepository attributeValueRepository;
    
}

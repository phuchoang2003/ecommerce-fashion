package org.example.ecommercefashion.services.impl;

import org.example.ecommercefashion.repositories.postgres.AttributeValueRepository;
import org.example.ecommercefashion.services.AttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {

    @Autowired
    private AttributeValueRepository attributeValueRepository;
    
}

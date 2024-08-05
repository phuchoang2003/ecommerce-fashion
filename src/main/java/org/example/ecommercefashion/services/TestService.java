package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.TestRequest;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.dtos.response.TestResponse;
import org.example.ecommercefashion.entities.TestEntity;
import org.springframework.data.domain.Pageable;

public interface TestService {

  TestResponse createTest(TestRequest testRequest);

  ResponsePage<TestEntity, TestResponse> findAll(Pageable pageable);
}

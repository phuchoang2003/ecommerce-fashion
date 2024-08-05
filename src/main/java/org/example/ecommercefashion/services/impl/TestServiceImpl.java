package org.example.ecommercefashion.services.impl;

import com.longnh.utils.FnCommon;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.TestRequest;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.dtos.response.TestResponse;
import org.example.ecommercefashion.entities.TestEntity;
import org.example.ecommercefashion.repositories.TestRepository;
import org.example.ecommercefashion.services.TestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

  private final TestRepository testRepository;

  @Override
  public TestResponse createTest(TestRequest testRequest) {
    TestEntity testEntity = new TestEntity();
    FnCommon.coppyNonNullProperties(testEntity, testRequest);
    testEntity = testRepository.save(testEntity);
    TestResponse testResponse = new TestResponse();
    FnCommon.coppyNonNullProperties(testResponse, testEntity);
    return testResponse;
  }

  @Override
  public ResponsePage<TestEntity, TestResponse> findAll(Pageable pageable) {
    Page<TestEntity> testEntities = testRepository.findAllTest(pageable);
    return new ResponsePage<>(testEntities, TestResponse.class);
  }
}

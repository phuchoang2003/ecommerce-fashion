package org.example.ecommercefashion.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.TestRequest;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.dtos.response.TestResponse;
import org.example.ecommercefashion.entities.TestEntity;
import org.example.ecommercefashion.services.TestService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/test")
@RestController
public class TestController {

  private final TestService testService;

  @GetMapping
  public ResponsePage<TestEntity, TestResponse> findAll(Pageable pageable) {
    return testService.findAll(pageable);
  }

  @PostMapping
  public TestResponse createTest(@Valid @RequestBody TestRequest testRequest) {
    return testService.createTest(testRequest);
  }
}

package org.example.ecommercefashion.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestRequest {
  
  @NotBlank(message = "Name is mandatory")
  @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
  private String name;
}

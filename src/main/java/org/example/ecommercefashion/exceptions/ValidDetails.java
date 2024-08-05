package org.example.ecommercefashion.exceptions;

import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidDetails {
  private Integer status;

  private Map<String, String> message;

  private String error;

  private String path;

  private Date timestamp;
}

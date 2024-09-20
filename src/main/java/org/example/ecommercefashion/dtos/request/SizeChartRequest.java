package org.example.ecommercefashion.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SizeChartRequest {
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 1, max = 255, message = "The length must be in range 1 to 255")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Length(min = 1, max = 255, message = "The length must be in range 1 to 255")
    private String description;
}

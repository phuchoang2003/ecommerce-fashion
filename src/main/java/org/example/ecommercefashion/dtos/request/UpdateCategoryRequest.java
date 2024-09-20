package org.example.ecommercefashion.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateCategoryRequest {

    @NotBlank(message = "Name cannot be blank")
    @Length(min = 1, max = 255, message = "The length must be in range 1 to 255")
    private String name;


    @NotNull(message = "Level cannot be null")
    @Size(min = 1, max = 10, message = "Category supports size chart from 1 to 10")
    private Set<@Positive(message = "Id must be greater than 0") Long> supportSizeChartIds;
}

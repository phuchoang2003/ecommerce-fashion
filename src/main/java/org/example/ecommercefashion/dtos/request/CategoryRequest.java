package org.example.ecommercefashion.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategoryRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private Long parentId;


    @NotNull(message = "Support size chart ids cannot be null")
    @Size(min = 1, max = 10, message = "The number of size charts must be between 1 and 10.")
    private Set<@Positive(message = "Id must be greater than 0") Long> supportSizeChartIds;

    @NotNull(message = "Attribute ids cannot be null")
    @Size(min = 1, max = 10, message = "The length of attributes ids must be between 1 and 10.")
    private Set<@Positive(message = "Id must be greater than 0") Long> attributeIds;


    @AssertTrue(message = "ParentId must be greater than 0 if present")
    private boolean isParentIdValid() {
        return parentId == null || parentId > 0;
    }

}

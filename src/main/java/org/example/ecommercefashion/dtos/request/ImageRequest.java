package org.example.ecommercefashion.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageRequest {
    @NotNull(message = "File is required!")
    @NotEmpty(message = "File is required!")
    @Size(min = 1, max = 5, message = "At least one image is required and at most 5 images")
    List<MultipartFile> files;
}

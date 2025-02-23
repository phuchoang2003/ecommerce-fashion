package org.example.ecommercefashion.common.storage.port;


import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.common.storage.dto.ImageRequest;
import org.example.ecommercefashion.common.storage.dto.ImageResponse;
import org.example.ecommercefashion.common.storage.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<List<ImageResponse>> uploadImages(@ModelAttribute ImageRequest files) {
        List<ImageResponse> response = imageService.uploadImages(files);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{idImage}")
    public ResponseEntity<ImageResponse> getImageById(@PathVariable Long idImage) {
        ImageResponse response = imageService.getImageById(idImage);
        return ResponseEntity.ok(response);
    }

}

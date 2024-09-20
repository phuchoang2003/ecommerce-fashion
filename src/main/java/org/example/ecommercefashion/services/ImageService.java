package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.ImageRequest;
import org.example.ecommercefashion.dtos.response.ImageResponse;
import org.example.ecommercefashion.entities.postgres.Image;
import org.example.ecommercefashion.entities.postgres.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    // createListImage
    List<ImageResponse> uploadImages(ImageRequest files);


    Image uploadImage(List<MultipartFile> file);

    List<ImageResponse> uploadImagesProduct(List<MultipartFile> files, Product product);

    // getImage
    ImageResponse getImageById(Long idImage);

    List<ImageResponse> getUrlsInStorage(List<Image> images);

    ImageResponse getUrlInStorage(Image image);


    // getImageBydUrl
    ImageResponse getImageBydUrl(String url);

    String getUrlInStorageByUrlInDb(String url);

    // deleteImage
    void deleteImage(Long idImage);

    //deleteImageByUrl
    void deleteImageByUrl(String url);

    Image getImage(Long idImage);

}

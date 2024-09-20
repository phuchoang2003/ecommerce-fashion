package org.example.ecommercefashion.utils;

import com.longnh.exceptions.ExceptionHandle;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUtils {

    private static final long MAX_TOTAL_FILE_SIZE = 5 * 1024 * 1024;


    public static void onlyAcceptOneFile(List<MultipartFile> files) {
        if (files.size() > 1) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.ONLY_ONE_FILE_ALLOWED.val());
        }
    }


    public static void isValidFiles(List<MultipartFile> files) {
        isValidExistFile(files);
        isValidLargeFiles(files);
        isValidContentType(files);
    }

    public static void isValidExistFile(List<MultipartFile> files) {
        if (files.isEmpty() || files.get(0).isEmpty()) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.FILE_NOT_FOUND.val());
        }
    }

    public static void isValidLargeFiles(List<MultipartFile> files) {
        long totalSize = files.stream().mapToLong(MultipartFile::getSize).sum();
        if (totalSize > MAX_TOTAL_FILE_SIZE) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.FILES_TOO_LARGE.val());
        }
    }

    public static void isValidContentType(List<MultipartFile> files) {
        Set<String> validContentType = new HashSet<>(List.of("image/jpeg", "image/png", "image/svg+xml"));
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            if (contentType == null || !validContentType.contains(contentType)) {
                throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_FILE_TYPE.val());
            }
        }
    }

}

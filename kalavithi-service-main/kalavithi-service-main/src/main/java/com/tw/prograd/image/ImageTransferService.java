package com.tw.prograd.image;

import com.tw.prograd.image.dto.Image;
import com.tw.prograd.image.dto.StoredImage;
import com.tw.prograd.image.dto.UploadImage;
import com.tw.prograd.image.storage.file.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.trimTrailingCharacter;

@Service
public class ImageTransferService {

    private final StorageService service;

    private final ImageRepository repository;

    public ImageTransferService(StorageService service, ImageRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    Resource imageByName(String name) {
        return service.load(name);
    }

    StoredImage images(String url) {

        List<Image> images = repository.findAll()
                .parallelStream()
                .map(it -> new Image(it.getId(), it.getName(), trimTrailingCharacter(url, '/') + "/" + it.getName(), it.getDescription()))
                .collect(toList());

        return new StoredImage(images);
    }

    UploadImage store(MultipartFile file) {
        service.store(file);
        ImageEntity imageEntity = new ImageEntity(null, file.getOriginalFilename(), "No description");
        return repository.save(imageEntity).toSavedImageDTO();
    }

    public String contentType(Resource image) {
        return service.contentType(image);
    }

}

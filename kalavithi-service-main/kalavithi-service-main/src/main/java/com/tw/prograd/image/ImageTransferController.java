package com.tw.prograd.image;

import com.tw.prograd.image.dto.StoredImage;
import com.tw.prograd.image.dto.UploadImage;
import com.tw.prograd.image.exception.ImageNotFoundException;
import com.tw.prograd.image.exception.ImageStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/images")
public class ImageTransferController {

    private final ImageTransferService service;
    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    public ImageTransferController(ImageTransferService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Resource> serveImage(@PathVariable String name) throws IOException {

        Resource image = service.imageByName(name);

        return status(OK)
                .header(CONTENT_TYPE, service.contentType(image))
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                .body(image);
    }

    @GetMapping
    public ResponseEntity<StoredImage> serveImages(HttpServletRequest request) {

        return status(OK)
                .body(service.images(request.getRequestURL().toString()));
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadImage> uploadImage(@RequestPart("image") MultipartFile file, RedirectAttributes redirectAttributes) {

        UploadImage image = service.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + image.getName() + "!");

        return status(FOUND)
                .location(URI.create(servletContextPath + "/images/" + image.getName()))
                .body(image);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    private ResponseEntity<?> handleImageNotFoundException(ImageNotFoundException exception) {

        return status(NOT_FOUND).build();
    }

    @ExceptionHandler(ImageStorageException.class)
    private ResponseEntity<?> handleImageStoreException(ImageStorageException exception) {

        return status(FORBIDDEN).build();
    }
}

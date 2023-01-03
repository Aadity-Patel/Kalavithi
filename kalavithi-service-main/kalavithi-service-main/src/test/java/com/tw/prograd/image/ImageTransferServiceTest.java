package com.tw.prograd.image;

import com.tw.prograd.image.dto.Image;
import com.tw.prograd.image.dto.StoredImage;
import com.tw.prograd.image.dto.UploadImage;
import com.tw.prograd.image.storage.file.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@ExtendWith(MockitoExtension.class)
class ImageTransferServiceTest {

    @Mock
    private StorageService storageService;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageTransferService imageTransferService;

    private MockMultipartFile image;

    private byte[] imageContent;

    @BeforeEach
    void setUp() {
        imageContent = "dummy image content".getBytes();
        image = new MockMultipartFile("image", "image.png", "multipart/form-data", imageContent);
    }

    @Test
    void shouldStoreImage() {
        doNothing().when(storageService).store(image);
        ImageEntity savedImage = new ImageEntity(1, "image.png", "Acrylic paint of tree at lake");
        when(imageRepository.save(any())).thenReturn(savedImage);

        UploadImage actual = imageTransferService.store(image);

        assertEquals(new UploadImage(1, "image.png"), actual);
        verify(storageService).store(image);
        verify(imageRepository).save(any());
    }

    @Test
    void shouldLoadImageWhenRequestedWithImageName() {
        Resource image = mock(Resource.class);
        when(storageService.load("image.png")).thenReturn(image);

        assertEquals(image, imageTransferService.imageByName("image.png"));

        verify(storageService).load("image.png");
    }

    @Test
    void shouldReturnImageMediaTypeWhenContentTypeRequested() {
        Resource image = mock(Resource.class);
        when(storageService.contentType(image)).thenReturn("image/png");

        assertEquals(IMAGE_PNG_VALUE, imageTransferService.contentType(image));

        verify(storageService).contentType(image);
    }

    @Test
    void shouldReturnsImagesWhenRequested() {
        when(imageRepository.findAll()).thenReturn(List.of(new ImageEntity(1, "image.png", "Acrylic paint of tree at lake")));

        String url = "http://localhost:8080/api/images/";
        StoredImage images = imageTransferService.images(url);

        assertEquals(new StoredImage(List.of(new Image(1, "image.png", url + "image.png", "Acrylic paint of tree at lake"))), images);

        verify(imageRepository).findAll();
    }

}
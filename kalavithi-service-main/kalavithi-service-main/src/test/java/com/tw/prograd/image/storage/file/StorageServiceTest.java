package com.tw.prograd.image.storage.file;

import com.tw.prograd.image.storage.file.config.StorageProperties;
import com.tw.prograd.image.storage.file.exception.EmptyFileException;
import com.tw.prograd.image.storage.file.exception.ImageNotFoundException;
import com.tw.prograd.image.storage.file.exception.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.System.getProperty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

class StorageServiceTest {

    private StorageProperties properties;

    private StorageService service;

    private String location;

    private MockMultipartFile image;

    private MockMultipartFile imageWithAbsolutePath;

    private MockMultipartFile imageWithNoContent;

    @BeforeEach
    public void setup() throws IOException {
        location = "build/files/" + Math.abs(new Random().nextLong());
        properties = new StorageProperties();
        properties.setInitialize(false);
        properties.setLocation(location);

        image = new MockMultipartFile("foo", "foo.png", IMAGE_PNG_VALUE, "Hello, World".getBytes());
        imageWithAbsolutePath = new MockMultipartFile("foo", "/etc/passwd", IMAGE_PNG_VALUE, "Hello, World".getBytes());
        imageWithNoContent = new MockMultipartFile("foo", "foo.png", IMAGE_PNG_VALUE, new byte[0]);

        service = new StorageService(properties, null);
        Files.createDirectories(Paths.get(location));
    }

    @Test
    void shouldImageStorageDirectoryBeCreatedWhenApplicationStarted() {
        assertTrue(Files.exists(Paths.get(properties.getLocation())));
    }

    @Test
    public void
    shouldStoreImageWhenProvided() {
        service.store(image);

        assertThat(Paths.get(properties.getLocation()).resolve("foo.png")).exists();
    }

    @Test
    public void shouldNotStoreImageWhenGivenImageHasNoContent() {
        assertThrows(EmptyFileException.class, () -> service.store(imageWithNoContent));
    }

    @Test
    public void shouldNotStoreImageWhenGivenImageHasAbsolutePath() {
        assertThrows(StorageException.class, () -> service.store(imageWithAbsolutePath));
    }

    @Test
    @Disabled
    void shouldRetrieveImageWhenGivenImageExist() throws IOException {
        service.store(image);

        Resource retrievedImage = service.load("foo.png");

        String imageContent = new BufferedReader(new InputStreamReader(retrievedImage.getInputStream())).lines()
                .collect(Collectors.joining("\n"));
        assertEquals("Hello, World", imageContent);
        assertEquals(getProperty("user.dir") + "/" + location + "/foo.png", retrievedImage.getURL().getPath());
    }

    @Test
    void shouldNotRetrieveImageWhenGivenImageDoesNotExist() {
        assertThrows(ImageNotFoundException.class, () -> service.load("nonExistingImage.png"));
    }


    @Test
    void shouldReturnImageMediaTypeWhenContentTypeRequested() {
        service.store(image);
        Resource image = service.load("foo.png");

        assertEquals(IMAGE_PNG_VALUE, service.contentType(image));
    }
}
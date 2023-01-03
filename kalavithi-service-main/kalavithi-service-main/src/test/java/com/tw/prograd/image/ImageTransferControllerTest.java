package com.tw.prograd.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.prograd.config.AuthenticationEntryPoint;
import com.tw.prograd.config.EncoderConfig;
import com.tw.prograd.config.SecurityConfig;
import com.tw.prograd.image.dto.UploadImage;
import com.tw.prograd.image.exception.ImageNotFoundException;
import com.tw.prograd.image.exception.ImageStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ImageTransferController.class)
@WithMockUser
@Import({SecurityConfig.class, EncoderConfig.class, AuthenticationEntryPoint.class})
class ImageTransferControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ImageTransferService imageTransferService;

    private MockMultipartFile image;

    private byte[] imageContent;

    @BeforeEach
    void setUp() {
        imageContent = "dummy image content".getBytes();
        image = new MockMultipartFile("image", "image.png", "multipart/form-data", imageContent);
    }

    @Test
    public void shouldReturnImageWhenExistingImageRequested() throws Exception {

        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(imageContent));
        when(resource.getFilename()).thenReturn("image.png");
        when(imageTransferService.imageByName("image.png")).thenReturn(resource);
        when(imageTransferService.contentType(resource)).thenReturn("image/png");

        mvc.perform(get("/images/image.png")).andExpect(status().isOk()).andExpect(header().stringValues(CONTENT_TYPE, "image/png")).andExpect(header().stringValues(CONTENT_DISPOSITION, "attachment; filename=\"image.png\"")).andExpect(content().bytes(imageContent));

        verify(imageTransferService).imageByName("image.png");
    }

    @Test
    @Disabled("GET and POST has same url, so url resolver could not resolve to correct route")
    public void shouldNotReturnAnythingWhenImageNameIsNotSent() throws Exception {

        mvc.perform(get("/images")).andExpect(status().isNotFound());

        verifyNoInteractions(imageTransferService);
    }

    @Test
    public void shouldNotReturnImageWhenNonExistingImageRequested() throws Exception {

        when(imageTransferService.imageByName("image.png")).thenThrow(ImageNotFoundException.class);

        mvc.perform(get("/images/image.png")).andExpect(status().isNotFound());

        verify(imageTransferService).imageByName("image.png");
    }

    @Test
    public void shouldSaveImageWhenUploaded() throws Exception {

        UploadImage uploadImage = new UploadImage(1, "image.png");
        when(imageTransferService.store(image)).thenReturn(uploadImage);

        this.mvc.perform(multipart("/images").file(image)).andExpect(status().isFound()).andExpect(header().string("Location", "/images/image.png")).andExpect(content().json(mapper.writeValueAsString(uploadImage)));

        verify(imageTransferService).store(image);
    }

    @Test
    public void shouldForbiddenTheUploadWhenFailedToStoreImage() throws Exception {

        doThrow(ImageStorageException.class).when(imageTransferService).store(image);

        this.mvc.perform(multipart("/images").file(image)).andExpect(status().isForbidden());

        verify(imageTransferService).store(image);
    }


}
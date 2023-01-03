package com.tw.prograd.likes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.prograd.config.AuthenticationEntryPoint;
import com.tw.prograd.config.EncoderConfig;
import com.tw.prograd.config.SecurityConfig;
import com.tw.prograd.image.ImageEntity;
import com.tw.prograd.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {LikesController.class})
@WebMvcTest(LikesController.class)
@WithMockUser
@Import({SecurityConfig.class, EncoderConfig.class, AuthenticationEntryPoint.class, LikesController.class})
public class LikeControllerTest {

    @MockBean
    LikesService likesService;
    LikesKey likesKey;
    LikesDTO likesDTO;
    ImageEntity imageEntity;
    UserEntity userEntity;
    Set<Integer> imageIdSet = new HashSet<>();
    Map<Integer, Integer> imageLikes = new HashMap<>();
    List<Map<Integer, Integer>> imageLikesCount = new ArrayList<>();
    String userName;
    String mobileNumber;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
         userName = "admin@test.com";
        mobileNumber = "6789123456";
        userEntity = UserEntity.builder().id(1).email(userName).mobileNumber(mobileNumber).build();
        imageEntity = new ImageEntity(1, "mi.png", "image");
        likesKey = new LikesKey(userEntity.getId(), imageEntity.getId());
        likesDTO = new LikesDTO(userEntity.getId(), imageEntity.getId());
        imageIdSet.add(imageEntity.getId());
        imageLikes.put(1, 1);
        imageLikesCount.add(imageLikes);
    }

    @Test
    public void shouldLikeImage() throws Exception {

        when(likesService.likeImage(likesDTO)).thenReturn(imageEntity.getId());
        mvc.perform(post("/users/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(likesDTO)))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnUserLikedImages() throws Exception {

        when(likesService.getUserLikedImages(userEntity.getId())).thenReturn(imageIdSet);
        mvc.perform(get("/users/like-image/{id}", userEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnImageLikesCount() throws Exception {

        when(likesService.getAllImageLikeCount()).thenReturn(imageLikesCount);
        mvc.perform(get("/users/image-like-count"))
                .andExpect(status().isOk());

    }
}

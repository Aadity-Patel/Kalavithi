package com.tw.prograd.likes;

import com.tw.prograd.image.ImageEntity;
import com.tw.prograd.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    LikesRepository likesRepository;
    @InjectMocks
    LikesService likesService;
    UserEntity userEntity;
    ImageEntity imageEntity;
    LikesKey likesKey;
    Optional<LikesEntity> likesEntityOptional;
    Set<Integer> imagesLikedByUser = new HashSet<>();
    LikesDTO likesDTO;
    Map<Integer, Integer> likesOnImage = new HashMap<>();
    List<Map<Integer, Integer>> listOfLikesOnImage = new ArrayList<>();

    @BeforeEach
    void setUp() {

        final String userName = "admin@test.com";
        final String mobileNumber = "6789123456";

        userEntity = UserEntity.builder().id(1).email(userName).mobileNumber(mobileNumber).build();
        imageEntity = new ImageEntity(1, "mi.png", "image");
        likesKey = new LikesKey(userEntity.getId(), imageEntity.getId());
        likesEntityOptional = Optional.of(new LikesEntity(likesKey, userEntity, imageEntity));
        imagesLikedByUser.add(imageEntity.getId());
        likesDTO = LikesDTO.builder().userId(userEntity.getId()).imageId(imageEntity.getId()).build();
        likesOnImage.put(1, 1);
        listOfLikesOnImage.add(likesOnImage);
    }

    @Test
    public void shouldLikeImage() {
        when(likesRepository.findById(likesKey)).thenReturn(likesEntityOptional);
        Integer actualId = likesService.likeImage(likesDTO);
        Integer expectedId = imageEntity.getId();

        assertThat(expectedId, is(equalTo(actualId)));

        verify(likesRepository, times(1)).findById(likesKey);
    }

    @Test
    public void shouldReturnListOfImages() {
        when(likesRepository.findUserLikedImages(userEntity.getId())).thenReturn(imagesLikedByUser);
        Set<Integer> expectedList = imagesLikedByUser;
        Set<Integer> actualList = likesService.getUserLikedImages(userEntity.getId());
        assertThat(expectedList, is(equalTo(actualList)));
        verify(likesRepository, times(1)).findUserLikedImages(userEntity.getId());
    }

    @Test
    public void shouldReturnCountOfLikesOnImage() {
        when(likesRepository.getLikesCount()).thenReturn(listOfLikesOnImage);
        List<Map<Integer, Integer>> expectedCount = listOfLikesOnImage;
        List<Map<Integer, Integer>> actualCount = likesService.getAllImageLikeCount();
        assertThat(expectedCount, is(equalTo(actualCount)));
        verify(likesRepository, times(1)).getLikesCount();
    }
}

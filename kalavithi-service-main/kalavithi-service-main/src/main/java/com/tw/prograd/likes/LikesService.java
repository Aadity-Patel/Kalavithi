package com.tw.prograd.likes;

import com.tw.prograd.image.ImageEntity;
import com.tw.prograd.image.ImageRepository;
import com.tw.prograd.user.UserEntity;
import com.tw.prograd.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class LikesService {
    @Autowired
    LikesRepository likesRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    UserRepository userRepository;


    public Integer likeImage(LikesDTO likesDto) {

        int userId = likesDto.getUserId();
        int imageId = likesDto.getImageId();

        LikesKey likesKey = new LikesKey(userId, imageId);
        Optional<LikesEntity> likesEntityOptional = likesRepository.findById(likesKey);

        if (likesEntityOptional.isPresent()) {
            // like is present so remove it
            LikesEntity likesEntity1 = likesEntityOptional.get();
            likesRepository.delete(likesEntity1);

        } else {
            // like is absent so add it
            Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
            if (!userEntityOptional.isPresent()) {
                // throw exception
                throw new DataIntegrityViolationException("User Not Present");
            }

            Optional<ImageEntity> imageEntityOptional = imageRepository.findById(imageId);
            if (!imageEntityOptional.isPresent()) {
                // throw error
                throw new DataIntegrityViolationException("Image Not Present");
            }
            LikesEntity likesEntity2 = LikesEntity.builder().id(likesKey).imageEntity(imageEntityOptional.get()).userEntity(userEntityOptional.get()).build();
            likesRepository.save(likesEntity2);
        }
        return imageId;
    }

    public Set<Integer> getUserLikedImages(Integer id) {
        Set<Integer> likes = likesRepository.findUserLikedImages(id);
        return likes;
    }

    public List<Map<Integer,Integer>> getAllImageLikeCount() {

        List<Map<Integer,Integer>> likesCount = likesRepository.getLikesCount();
        return likesCount;
    }
}

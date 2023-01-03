package com.tw.prograd.likes;

import com.tw.prograd.image.ImageEntity;
import com.tw.prograd.user.UserEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "likes")
public class LikesEntity {

    @EmbeddedId
    LikesKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    UserEntity userEntity;

    @ManyToOne
    @MapsId("imageId")
    @JoinColumn(name = "image_id")
    ImageEntity imageEntity;


}


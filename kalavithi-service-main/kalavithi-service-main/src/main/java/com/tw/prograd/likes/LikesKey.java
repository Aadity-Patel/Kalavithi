package com.tw.prograd.likes;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
class LikesKey implements Serializable {

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "image_id")
    Integer imageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikesKey likesKey = (LikesKey) o;
        return Objects.equals(userId, likesKey.userId) && Objects.equals(imageId, likesKey.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, imageId);
    }
}
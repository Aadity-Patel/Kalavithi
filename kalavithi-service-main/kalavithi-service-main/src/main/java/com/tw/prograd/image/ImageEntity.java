package com.tw.prograd.image;

import com.tw.prograd.image.dto.UploadImage;
import com.tw.prograd.likes.LikesEntity;
import lombok.*;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    public UploadImage toSavedImageDTO() {
        return new UploadImage(id, name);
    }
}

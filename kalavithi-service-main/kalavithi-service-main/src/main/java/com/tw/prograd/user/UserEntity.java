package com.tw.prograd.user;

import com.tw.prograd.likes.LikesEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "user_profile")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Integer id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String mobileNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

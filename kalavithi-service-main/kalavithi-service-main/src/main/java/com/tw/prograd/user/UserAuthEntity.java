package com.tw.prograd.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;

import static java.util.Collections.emptyList;

@Entity(name = "user_auth")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserAuthEntity implements UserDetails {

    @Id
    private Integer id;

    @NonNull
    private String password;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return emptyList();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

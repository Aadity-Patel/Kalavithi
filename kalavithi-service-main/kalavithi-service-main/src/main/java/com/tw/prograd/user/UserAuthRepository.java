package com.tw.prograd.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Integer> {
    Optional<UserAuthEntity> findById(Integer id);
}

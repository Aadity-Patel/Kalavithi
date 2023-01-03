package com.tw.prograd.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, LikesKey> {

    @Query(value = "select c.image_id from likes c where c.user_id=:user_id", nativeQuery = true)
    Set<Integer> findUserLikedImages(@Param("user_id") Integer user_id);

    @Query(value = "select c.image_id , count(c.user_id) from likes c group by c.image_id", nativeQuery = true)
    List<Map<Integer,Integer>> getLikesCount();
}
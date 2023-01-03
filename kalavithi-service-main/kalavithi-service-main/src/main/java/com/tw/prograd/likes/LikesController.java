package com.tw.prograd.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;
@RestController
@RequestMapping("/users")
public class LikesController {

    @Autowired
    LikesService likesService;

    @GetMapping("/like-image/{id}")
    public ResponseEntity<Set<Integer>> userLikedImages(@PathVariable Integer id) {

        Set<Integer> likes = likesService.getUserLikedImages(id);
        return new ResponseEntity<>(likes, HttpStatus.OK);

    }

    @PostMapping("/like")
    public ResponseEntity<Integer> addUserLike(@RequestBody LikesDTO likes) throws Exception {
        try {
            Integer imageId = likesService.likeImage(likes);
            return new ResponseEntity<>(imageId, HttpStatus.OK);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not like/dislike post");
        }
    }

    @GetMapping("/image-like-count")
    public ResponseEntity<List<Map<Integer,Integer>>> getImageLikeCount() {
        List<Map<Integer,Integer>> likesCount = likesService.getAllImageLikeCount();

        return new ResponseEntity<>(likesCount, HttpStatus.OK);
    }
}

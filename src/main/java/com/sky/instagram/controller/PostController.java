package com.sky.instagram.controller;

import com.sky.instagram.exeption.PostException;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.Post;
import com.sky.instagram.model.User;
import com.sky.instagram.response.MessageResponse;
import com.sky.instagram.service.PostService;
import com.sky.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post,@RequestHeader("Authorization") String token)throws UserException {
        User user = userService.findUserProfile(token);
        Post createdPost = postService.createPost(post, user.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable("id") Integer userId) throws UserException {
        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/following/{ids}")
    public ResponseEntity<List<Post>> findAllPostByUserIdHandler(@PathVariable("ids") List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postService.findAllPostByUserIds(userIds);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws PostException{
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException{
        User user = userService.findUserProfile(token);
        Post likedPost = postService.likePost(postId, user.getId());

        return new ResponseEntity<>(likedPost, HttpStatus.OK);
    }

    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unlikePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException{
        User user = userService.findUserProfile(token);
        Post likedPost = postService.unlikePost(postId, user.getId());

        return new ResponseEntity<>(likedPost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException{
        User user = userService.findUserProfile(token);
        String message = postService.deletePost(postId, user.getId());
        MessageResponse res = new MessageResponse(message);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/save_post/{postId}")
    public ResponseEntity<MessageResponse> savedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException {
        User user = userService.findUserProfile(token);
        String message = postService.savedPost(postId, user.getId());
        MessageResponse res = new MessageResponse(message);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unsavedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException {
        User user = userService.findUserProfile(token);
        String message = postService.unSavedPost(postId, user.getId());
        MessageResponse res = new MessageResponse(message);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
}

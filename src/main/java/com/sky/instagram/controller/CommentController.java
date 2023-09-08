package com.sky.instagram.controller;

import com.sky.instagram.exeption.CommentException;
import com.sky.instagram.exeption.PostException;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.Comment;
import com.sky.instagram.model.User;
import com.sky.instagram.repository.CommentRepository;
import com.sky.instagram.service.CommentService;
import com.sky.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<Comment> createCommentHandler(@PathVariable Integer postId, @RequestBody Comment comment, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        Comment createdComment = commentService.createComment(comment, postId, user.getId());
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<Comment> likeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) throws UserException, CommentException{
        User user = userService.findUserProfile(token);
        Comment comment = commentService.likeComment(commentId, user.getId());

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/unlike/{commentId}")
    public ResponseEntity<Comment> unlikeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) throws UserException, CommentException{
        User user = userService.findUserProfile(token);
        Comment comment = commentService.unlikeComment(commentId, user.getId());

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}

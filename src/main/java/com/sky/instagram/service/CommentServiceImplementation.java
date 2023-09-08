package com.sky.instagram.service;

import com.sky.instagram.dto.UserDto;
import com.sky.instagram.exeption.CommentException;
import com.sky.instagram.exeption.PostException;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.Comment;
import com.sky.instagram.model.Post;
import com.sky.instagram.model.User;
import com.sky.instagram.repository.CommentRepository;
import com.sky.instagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImplementation implements CommentService{
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    private final PostRepository postRepository;
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.setUser(userDto);
        comment.setCreatedAt(LocalDateTime.now());

        Comment createdComment = commentRepository.save(comment);
        post.getComments().add(createdComment);
        postRepository.save(post);
        return createdComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {
        Optional<Comment> opt = commentRepository.findById(commentId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new CommentException("Comment is not exist with id: "+ commentId);
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.getLikedByUsers().add(userDto);

        return commentRepository.save(comment);
    }

    @Override
    public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.getLikedByUsers().remove(userDto);

        return commentRepository.save(comment);
    }
}

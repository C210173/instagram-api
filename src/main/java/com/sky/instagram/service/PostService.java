package com.sky.instagram.service;

import com.sky.instagram.dto.UserDto;
import com.sky.instagram.exeption.PostException;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post, Integer userId) throws UserException;
    String deletePost(Integer postId, Integer userId) throws UserException, PostException;
    List<Post> findPostByUserId(Integer userId) throws UserException;
    Post findPostById(Integer postId) throws PostException;
    List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException;
    String savedPost(Integer postId, Integer userId) throws PostException, UserException;
    String unSavedPost(Integer postId, Integer userId) throws PostException, UserException;
    Post likePost(Integer postId, Integer userId) throws UserException, PostException;
    Post unlikePost(Integer postId, Integer userId) throws UserException, PostException;

    void updatePostsWhenUserUpdate(UserDto updatedUser, Integer userId) throws UserException;

}

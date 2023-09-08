package com.sky.instagram.service;

import com.sky.instagram.exeption.CommentException;
import com.sky.instagram.exeption.PostException;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.Comment;

public interface CommentService {
    Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException;
    Comment findCommentById(Integer commentId)throws CommentException;
    Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException;
    Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException;

}

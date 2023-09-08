package com.sky.instagram.service;

import com.sky.instagram.dto.UserDto;
import com.sky.instagram.exeption.PostException;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.Post;
import com.sky.instagram.model.User;
import com.sky.instagram.repository.PostRepository;
import com.sky.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService{
    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    @Override
    public Post createPost(Post post, Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.setUser(userDto);
        post.setCreatedAt(LocalDateTime.now());

        Post createdPost = postRepository.save(post);
        return createdPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (post.getUser().getId().equals(user.getId())){
            postRepository.deleteById(userId);
            return "Post Delete Successfully";
        }
        throw new PostException("You can't delete other user's post");
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepository.findByUserId(userId);
        if (posts.size()==0){
            throw new UserException("this user does not any post");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> opt = postRepository.findById(postId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new PostException("Post not found with id " + postId);
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postRepository.findAllPostByUserIds(userIds);
        if(posts.size()==0){
            throw new PostException("No post available");
        }
        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (!user.getSavedPost().contains(post)){
           user.getSavedPost().add(post);
           userRepository.save(user);
        }
        return "Post Saved Successfully";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
            userRepository.save(user);
        }
        return "Post Remove Successfully";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().add(userDto);

        return postRepository.save(post);
    }

    @Override
    public Post unlikePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().remove(userDto);

        return postRepository.save(post);
    }

    @Override
    public void updatePostsWhenUserUpdate(UserDto updatedUser, Integer userId) throws UserException {
        List<Post> userPosts = findPostByUserId(userId);
        for (Post post : userPosts) {
            post.getUser().setUserImage(updatedUser.getUserImage());
            post.getUser().setUsername(updatedUser.getUsername());
            post.getUser().setName(updatedUser.getName());
            post.getUser().setEmail(updatedUser.getEmail());
        }
        postRepository.saveAll(userPosts);
    }
}

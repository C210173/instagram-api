package com.sky.instagram.controller;

import com.sky.instagram.dto.UserDto;
import com.sky.instagram.exeption.UserException;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer id) throws UserException{
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsernameHandler(@PathVariable String username) throws UserException{
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<MessageResponse> followUserHandler(@PathVariable Integer followUserId,
                                                             @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        String message = userService.followUser(user.getId(), followUserId);
        MessageResponse res = new MessageResponse(message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/unfollow/{userId}")
    public ResponseEntity<MessageResponse> unFollowUserHandler(@PathVariable Integer userId,
                                                             @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        String message = userService.unFollowUser(user.getId(), userId);
        MessageResponse res = new MessageResponse(message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/req")
    public ResponseEntity<User> findUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<User>> findUserByUserIdsHandler(@PathVariable List<Integer> userIds) throws UserException {
        List<User> users = userService.findUserByIds(userIds);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/search/")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("query") String query) throws UserException {
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/account/edit")
    public ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization") String token, @RequestBody User user) throws UserException {
        User reqUser = userService.findUserProfile(token);
        User updateUser = userService.updateUserDetails(user, reqUser);
        UserDto userDto = new UserDto();
        userDto.setId(updateUser.getId());
        userDto.setUsername(updateUser.getUsername());
        userDto.setName(updateUser.getName());
        userDto.setEmail(updateUser.getEmail());
        userDto.setUserImage(updateUser.getImage());
        postService.updatePostsWhenUserUpdate(userDto, reqUser.getId());
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<User>> findPopularUsersHandler() throws UserException {
        List<User> popularUsers = userService.findPopularUsers(6); // 6 là số lượng người dùng phổ biến bạn muốn lấy
        return new ResponseEntity<>(popularUsers, HttpStatus.OK);
    }
}

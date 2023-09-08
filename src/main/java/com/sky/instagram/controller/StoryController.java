package com.sky.instagram.controller;

import com.sky.instagram.exeption.StoryException;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.Story;
import com.sky.instagram.model.User;
import com.sky.instagram.service.StoryService;
import com.sky.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
public class StoryController {
    private final StoryService storyService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story, @RequestHeader("Authorization") String token)throws UserException{
        User user = userService.findUserProfile(token);
        Story createdStory = storyService.createStory(story, user.getId());
        return new ResponseEntity<>(createdStory, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findAllStoryByUserIdHandler(@PathVariable Integer userId) throws UserException, StoryException {
        User user = userService.findUserById(userId);
        List<Story> stories = storyService.findStoryByUserId(userId);
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }
}

package com.sky.instagram.service;

import com.sky.instagram.exeption.StoryException;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.Story;

import java.util.List;

public interface StoryService {
    Story createStory(Story story, Integer userId) throws UserException;
    List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;
}

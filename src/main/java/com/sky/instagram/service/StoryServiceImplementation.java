package com.sky.instagram.service;

import com.sky.instagram.dto.UserDto;
import com.sky.instagram.exeption.StoryException;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.Story;
import com.sky.instagram.model.User;
import com.sky.instagram.repository.StoryRepository;
import com.sky.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryServiceImplementation implements StoryService{
    private final StoryRepository storyRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    @Override
    public Story createStory(Story story, Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        story.setUser(userDto);
        story.setTimestamp(LocalDateTime.now());
        user.getStories().add(story);
//        userRepository.save(user);
        return storyRepository.save(story);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
        User user = userService.findUserById(userId);
        List<Story> stories = user.getStories();
        if (stories.size()==0){
            throw new StoryException("this user doesn't have any story");
        }
        return stories;
    }
}

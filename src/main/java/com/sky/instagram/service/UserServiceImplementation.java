package com.sky.instagram.service;

import com.sky.instagram.dto.UserDto;
import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.User;
import com.sky.instagram.repository.UserRepository;
import com.sky.instagram.security.JwtTokenClaims;
import com.sky.instagram.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public User registerUser(User user) throws UserException {
        Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist.isPresent()){
            throw new UserException("Email Is Already Exist");
        }
        Optional<User> isUsernameExist = userRepository.findByUsername(user.getUsername());
        if (isUsernameExist.isPresent()){
            throw new UserException("Username Is Already taken...");
        }
        if (user.getEmail()==null || user.getPassword()==null || user.getUsername()==null || user.getName()==null){
            throw new UserException("All fields are required");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new UserException("user not exist with id: "+ userId);
    }

    @Override
    public User findUserProfile(String token) throws UserException {
        token=token.substring(7);
        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);
        String email = jwtTokenClaims.getUsername();
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new UserException("invalid token...");
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> opt = userRepository.findByUsername(username);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new UserException("user not found");
    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getImage());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUserImage(followUser.getImage());
        following.setUsername(followUser.getUsername());

        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "you are following " + followUser.getUsername();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getImage());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUserImage(followUser.getImage());
        following.setUsername(followUser.getUsername());

        reqUser.getFollowing().remove(following);
        followUser.getFollower().remove(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);
        return "You have unfollowed " + followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        List<User> users = userRepository.findAllUsersByUserIds(userIds);
        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> users = userRepository.findByQuery(query);
        if(users.size()==0){
            throw new UserException("user not found");
        }
        return users;
    }

    @Override
    public User updateUserDetails(User updateUser, User existingUser) throws UserException {
        if (updateUser.getEmail() != null){
            existingUser.setEmail(updateUser.getEmail());
        }
        if (updateUser.getBio() != null){
            existingUser.setBio(updateUser.getBio());
        }
        if (updateUser.getName() != null){
            existingUser.setName(updateUser.getName());
        }
        if (updateUser.getUsername() != null){
            existingUser.setUsername(updateUser.getUsername());
        }
        if (updateUser.getMobile() != null){
            existingUser.setMobile(updateUser.getMobile());
        }
        if (updateUser.getGender() != null){
            existingUser.setGender(updateUser.getGender());
        }
        if (updateUser.getWebsite() != null){
            existingUser.setWebsite(updateUser.getWebsite());
        }
        if (updateUser.getImage() != null){
            existingUser.setImage(updateUser.getImage());

        }
        if (updateUser.getId().equals(existingUser.getId())){
            return userRepository.save(existingUser);
        }
        throw new UserException("you can't update this user");
    }


    @Override
    public List<User> findPopularUsers(int limit) throws UserException {
        List<User> popularUsers = userRepository.findPopularUsers();
        if (popularUsers.size() > limit) {
            return popularUsers.subList(0, limit);
        }
        return popularUsers;
    }

}

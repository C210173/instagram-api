package com.sky.instagram.service;

import com.sky.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.sky.instagram.model.User> opt = userRepository.findByEmail(username);
        if (opt.isPresent()){
            com.sky.instagram.model.User user = opt.get();

            List<GrantedAuthority> authorities = new ArrayList<>();


            return new User(user.getEmail(), user.getPassword(), authorities);
        }
        throw new BadCredentialsException("User not found with username" + username);
    }
}

package org.example.ra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.ra.model.User;
import org.example.ra.dto.UserResponse;
import org.example.ra.repo.UserRepository;
import org.example.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<UserResponse> userList(int page, int size, Sort sort) {
        return userRepository.findAll(PageRequest.of(page, size, sort))
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()));
    }

    public User getUserId (Long userId) {
        return userRepository.findById(userId).orElseThrow(()->{
            log.error("Failed to find cinema [{}]", userId);
          return new RuntimeException("User not found!");
        });
    }

    public User createUser(User user) {
        try {
            log.info("Creating user [{}]", user);
            return userRepository.save(user);
        }catch (Exception e){
            log.error("Failed to create user [{}]", user.getId());
            throw new RuntimeException("Failed to create user!");
        }
    }
}

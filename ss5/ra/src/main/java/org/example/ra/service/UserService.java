package org.example.ra.service;

import org.example.ra.model.User;
import org.example.ra.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface UserService {
    Page<UserResponse> userList(int page, int size, Sort sort);
    User getUserId (Long userId);
    User createUser(User user);
}

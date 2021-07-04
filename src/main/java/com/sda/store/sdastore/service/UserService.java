package com.sda.store.sdastore.service;

import com.sda.store.sdastore.controller.dto.user.UserDto;
import com.sda.store.sdastore.model.User;

public interface UserService {

    User create(User user);
    User findByEmail(String email);
    User update (User user);
    User findById(Long id);
}

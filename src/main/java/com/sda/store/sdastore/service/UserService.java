package com.sda.store.sdastore.service;

import com.sda.store.sdastore.model.User;

public interface UserService {

    User create(User user);
    User findByEmail(String email);
}

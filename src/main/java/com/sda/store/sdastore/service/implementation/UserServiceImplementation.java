package com.sda.store.sdastore.service.implementation;

import com.sda.store.sdastore.exception.ResourceAlreadyPresentInDatabase;
import com.sda.store.sdastore.model.Product;
import com.sda.store.sdastore.model.User;
import com.sda.store.sdastore.repository.UserRepository;
import com.sda.store.sdastore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImplementation (UserRepository userRepository){
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public User create(User user) {
        User userInDatabase = userRepository.findByEmail(user.getEmail());
        if (userInDatabase != null){
            throw new ResourceAlreadyPresentInDatabase(String.format("User with email %s already exists.", user.getEmail()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public User update (User user) {

        User existingUser = userRepository.findById(user.getId()).orElse(null);

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }
}

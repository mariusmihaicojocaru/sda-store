package com.sda.store.sdastore.controller;

import com.sda.store.sdastore.config.ExceptionControllerAdvice;
import com.sda.store.sdastore.controller.dto.user.AddressDto;
import com.sda.store.sdastore.controller.dto.user.UserDto;
import com.sda.store.sdastore.model.*;
import com.sda.store.sdastore.service.RoleService;
import com.sda.store.sdastore.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sda.store.sdastore.controller.dto.user.AddressDto.mapAddressToAddressDto;

@RestController
public class UserController {

    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping(value = "/register")
    public UserDto create(@RequestBody UserDto userDto) {
        User user = userService.create(mapUserDtoToUser(userDto));
        return mapUserToUserDto(user);
    }

    @GetMapping(value = "/users/roles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @PostMapping(value = "/users/login")
    public org.springframework.security.core.userdetails.User login() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping(value = "/users/{email}")
    public UserDto getUserByEmail(@PathVariable("email") String email){
        return mapUserToUserDto(userService.findByEmail(email));
    }

    @PutMapping(value = "/users/{email}")
    public UserDto update(@PathVariable("email") String email, @RequestBody UserDto userDto){
        User updatedUser = updateUserDtoToUser(userService.findByEmail(email), userDto);
        return mapUserToUserDto(userService.update(updatedUser));
    }


    private UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setImageUrl(user.getImageUrl());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMessagingChannel(user.getMessagingChannel());

        Set<Role> userRoles = user.getRole();
        for (Role role: userRoles) {
            userDto.setRole(role.getName());
        }

        userDto.setAddress(mapAddressToAddressDto(user.getAddress()));
        return userDto;
    }

    private User mapUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setImageUrl(userDto.getImageUrl());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMessagingChannel(userDto.getMessagingChannel());
        Role role = roleService.findByName(userDto.getRole());
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setAddress(mapAddressDtoToAddress(userDto.getAddress()));
        return user;
    }

    private Address mapAddressDtoToAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setZipcode(addressDto.getZipcode());
        address.setStreet(addressDto.getStreet());
        return address;
    }

    private AddressDto mapAddressToAddressDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipcode(address.getZipcode());
        addressDto.setStreet(address.getStreet());
        return addressDto;
    }

    private User updateUserDtoToUser(User user, UserDto userDto){

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setAddress(mapAddressDtoToAddress(userDto.getAddress()));
        user.setPassword(userDto.getPassword());

        return user;
    }

    private UserDto updateUserToUserDto(User user){

        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(mapAddressToAddressDto(user.getAddress()));
        userDto.setPassword(user.getPassword());

        return userDto;
    }

}

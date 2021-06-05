package com.sda.store.sdastore.service;

import com.sda.store.sdastore.model.Role;

import java.util.List;

public interface RoleService {

    Role create(Role role);
    Role findByName(String name);
    List<Role> findAll();

}

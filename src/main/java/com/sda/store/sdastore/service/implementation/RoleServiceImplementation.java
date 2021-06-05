package com.sda.store.sdastore.service.implementation;

import com.sda.store.sdastore.model.Role;
import com.sda.store.sdastore.repository.RoleRepository;
import com.sda.store.sdastore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImplementation implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImplementation(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        Role roleInDatabase = findByName(role.getName());
        if(roleInDatabase != null){
            return roleInDatabase;
        }
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
       return roleRepository.findByName(name);
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}

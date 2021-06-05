package com.sda.store.sdastore.config;

import com.sda.store.sdastore.exception.ResourceNotFoundInDatabase;
import com.sda.store.sdastore.model.Role;
import com.sda.store.sdastore.model.User;
import com.sda.store.sdastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SdaStoreUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public SdaStoreUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new ResourceNotFoundInDatabase(String.format("User with email %s was not found.", email));
        }
        return mapUserToUserDetails(user);
    }

    public UserDetails mapUserToUserDetails(User user){
        List<GrantedAuthority> grantedAuthorities = mapRoleToGrantedAuthority(user.getRole());
        UserDetails userDetails = new org.springframework
                .security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
        return userDetails;
    }

    public List<GrantedAuthority> mapRoleToGrantedAuthority(Set<Role> roles){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role role: roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorities;
    }
}

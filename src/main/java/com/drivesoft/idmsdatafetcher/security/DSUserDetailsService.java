package com.drivesoft.idmsdatafetcher.security;

import com.drivesoft.idmsdatafetcher.entity.User;
import com.drivesoft.idmsdatafetcher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DSUserDetailsService {
    @Autowired
    UserRepository userRepository;

    public Boolean isUserExist(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        return user.isPresent();
    }
}

package com.web.shop.webbanhang.security;

import com.web.shop.webbanhang.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.trace("Entering method loadUserByUsername");
        LOGGER.debug("Authenticating user with username: "+username);

        User user = userRepository.getUserByUsername(username);

        if (user == null){
            LOGGER.error("User not found.");
            throw new UsernameNotFoundException("Could not find user");
        }

        LOGGER.warn("We are testing logging with Spring Boot...");
        LOGGER.info("User authenticated successfully");
        return new MyUserDetails(user);
    }

}

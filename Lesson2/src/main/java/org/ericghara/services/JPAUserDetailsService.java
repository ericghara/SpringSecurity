package org.ericghara.services;

import org.ericghara.entities.User;
import org.ericghara.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JPAUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JPAUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User u = userRepository.findUserByUsername(username)
                               .orElseThrow(() -> new UsernameNotFoundException("Could not locate username:" + username) );
        return new SecurityUser(u);
    }

}

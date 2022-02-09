package org.ericghara.services;

import org.ericghara.entities.User;
import org.ericghara.repositories.UserRepository;
import org.ericghara.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = userRepository.findUserByUsername(username);
        return new SecurityUser(u.orElseThrow( () ->
                new UsernameNotFoundException("Could not locate the specified username.") ) );
    }

    @Transactional(readOnly = true)
    public int getIdByUsername(String username) throws UsernameNotFoundException {
        return loadUserByUsername(username).getId();
    }
    
    public SecurityUser getUserDetails(User user) {
        return new SecurityUser(user);
    }
}

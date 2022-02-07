package org.ericghara.services;

import org.ericghara.repositories.UserRepository;
import org.ericghara.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = userRepository.findUserByUsername(username);
        return new SecurityUser(u.orElseThrow( () ->
                new UsernameNotFoundException("Could not locate the specified username.") ) );
    }

    public int getIdByUsername(String username) throws UsernameNotFoundException {
        return loadUserByUsername(username).getId();
    }
}

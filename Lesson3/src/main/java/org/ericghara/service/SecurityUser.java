package org.ericghara.service;

import org.ericghara.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Wrapper class for {@link org.ericghara.model.User} allowing translation of data into
 * {@link org.springframework.security.core.userdetails.UserDetails}.  Note this wrapper
 * hashes the {@link org.ericghara.model.User}s plaintext password using the provided
 * {@link org.springframework.security.crypto.password.PasswordEncoder}.
 */
public class SecurityUser implements UserDetails {

    private final User user;
    private final PasswordEncoder passwordEncoder;

    public SecurityUser(User user, PasswordEncoder passwordEncoder) {
        this.user = user;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities()
                   .stream()
                   .map(SimpleGrantedAuthority::new)
                   .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return passwordEncoder.encode(user.getPassword() );
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

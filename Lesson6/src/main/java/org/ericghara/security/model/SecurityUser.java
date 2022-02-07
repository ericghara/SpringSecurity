package org.ericghara.security.model;

import org.ericghara.entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecurityUser implements UserDetails  {
    
    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return Stream.of(user.getAuthorities().split("\\s") )
                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toList());
    }

    public int getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
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

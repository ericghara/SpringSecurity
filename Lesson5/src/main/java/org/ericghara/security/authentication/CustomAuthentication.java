package org.ericghara.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthentication extends UsernamePasswordAuthenticationToken {

    // This constructor creates an Unauthenticated token
    public CustomAuthentication(Object principal) {
        super(principal, null);
    }

    // This constructor creates an AuthenticationToken  set to Authenticated
    public CustomAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}

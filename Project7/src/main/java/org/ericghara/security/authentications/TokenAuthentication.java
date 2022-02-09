package org.ericghara.security.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication extends UsernamePasswordAuthenticationToken {

    public TokenAuthentication(String credentials) {
        super("", credentials);
    }

    public TokenAuthentication(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}

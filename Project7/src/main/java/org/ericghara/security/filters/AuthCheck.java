package org.ericghara.security.filters;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface AuthCheck<T extends ServletRequest,
        U extends Authentication,
        V extends ServletResponse> {

    Authentication authenticate(T request, V response);

    void onFailure(AuthenticationException exception, U authentication, V response);

    void onSuccess(U authentication, V response);
}

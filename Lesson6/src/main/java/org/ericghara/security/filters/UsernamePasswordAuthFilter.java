package org.ericghara.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.ericghara.services.RequestHeaderKeys.OTP;
import static org.ericghara.services.RequestHeaderKeys.PASSWORD;

/*
    Implements 2FA using a username/password login and a one time password.
    A valid username and password login leads to an OTP being created in the OtpRepository.
    If the client sends back a valid username/OTP they are granted a UUID token
    (response header, authorization:XXXXX)
 */
@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private final OtpAuthCheck otpAuthCheck;
    private final UsernamePasswordAuthCheck usernamePasswordAuthCheck;


    @Autowired
    public UsernamePasswordAuthFilter(OtpAuthCheck otpAuthCheck, UsernamePasswordAuthCheck usernamePasswordAuthCheck) {
        this.otpAuthCheck = otpAuthCheck;
        this.usernamePasswordAuthCheck = usernamePasswordAuthCheck;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        // Step 1: username & password
        // Step 2: username & otp
        // (or vice versa)

        if (OTP.in(request) ) {
            otpAuthCheck.authenticate(request, response);
        }
        else if(PASSWORD.in(request) ) {
            usernamePasswordAuthCheck.authenticate(request, response);
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

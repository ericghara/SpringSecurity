package org.ericghara.security.providers;

import org.ericghara.security.authentications.OtpAuthentication;
import org.ericghara.entities.Otp;
import org.ericghara.security.model.SecurityUser;
import org.ericghara.services.JpaUserDetailsService;
import org.ericghara.services.OtpService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final JpaUserDetailsService userDetailsService;
    private final OtpService otpService;

    public OtpAuthenticationProvider(JpaUserDetailsService userDetailsService, OtpService otpService) {
        this.userDetailsService = userDetailsService;
        this.otpService = otpService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String receivedOtp = authentication.getCredentials().toString();
        SecurityUser su = userDetailsService.loadUserByUsername(username);
        Otp otp = otpService.getOtp(su.getId() );
        if (receivedOtp.length() == otpService.otpLength() &&  // prevents validating null otp in repository
                receivedOtp.equals(otp.getOtp() ) ) {
            return new OtpAuthentication(username, otp, su.getAuthorities());
        }
        throw new BadCredentialsException("Failure to authenticate the username / otp pair");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}

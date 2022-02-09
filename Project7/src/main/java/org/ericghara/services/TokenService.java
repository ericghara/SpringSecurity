package org.ericghara.services;

import org.ericghara.entities.Token;
import org.ericghara.entities.User;
import org.ericghara.repositories.TokenRepository;
import org.ericghara.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {


    private final long LIFETIME;
    private static final long MILLI_PER_SEC = 1000L;

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository,
                        @Value("${org.ericghara.token.lifetime}") long lifetime) {
        this.tokenRepository = tokenRepository;
        LIFETIME = lifetime;
    }

    public Token getToken(String authToken) throws BadCredentialsException {
        return tokenRepository.findTokenByToken(authToken).orElseThrow(
                () -> new BadCredentialsException("The provided Token could not be found") );
    }

    User getUser(Token tokenEntity) {
        return tokenEntity.getUser();
    }

    public SecurityUser getUserDetails(Token token) {
        return new SecurityUser(token.getUser() );
    }

    private Instant expirationOf(Instant grantTime) {
        return grantTime.plusSeconds(LIFETIME);
    }

    public void assertActive(Token token) throws BadCredentialsException {
        Instant grantTime = token.getGrantTime();
        Instant expirationTime = expirationOf(grantTime);
        Instant now = Instant.now();
        if ( now.isAfter(expirationTime) ) {
            long secPastExp = ( now.toEpochMilli() - expirationTime.toEpochMilli() ) / MILLI_PER_SEC;
            var msg = String.format("Token %s expired %d seconds ago.",
                    token.getToken(), secPastExp);
            throw new BadCredentialsException(msg);
        }
    }

    public void grantToken(int userId) {


    }
}

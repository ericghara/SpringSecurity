package org.ericghara.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

// Simple demo of a custom repository.
@Repository
public class CustomCsrfRepository implements CsrfTokenRepository {

    private UUID insecureCsrf;

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        // Just a very insecure demo to show how to implement CsrfTokenRepository
        return new DefaultCsrfToken( "X-CSRF-TOKEN", "_csrf", insecureCsrf.toString() );
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        insecureCsrf = UUID.randomUUID();
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        if (Objects.isNull(insecureCsrf) ) {
            insecureCsrf = UUID.randomUUID();
        }
        return new DefaultCsrfToken( "X-CSRF-TOKEN", "_csrf", insecureCsrf.toString() );
    }
}

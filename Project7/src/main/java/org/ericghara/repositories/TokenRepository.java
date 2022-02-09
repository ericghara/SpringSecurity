package org.ericghara.repositories;

import org.ericghara.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findTokenById(int id);

    Optional<Token> findTokenByToken(String token);
}

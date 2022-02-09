package org.ericghara.repositories;

import org.ericghara.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

    Optional<Otp> findOtpById(int id);

}

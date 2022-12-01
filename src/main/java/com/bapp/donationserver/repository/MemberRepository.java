package com.bapp.donationserver.repository;

import com.bapp.donationserver.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"wallet"})
    Optional<Member> findWithWalletByEmail(String email);
}

package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.dto.MemberDto;

public interface MemberRepository {
    void save(MemberDto member);
    void update(String email, MemberDto member);
    void delete(String email);
    Member findByEmail(String email);
}

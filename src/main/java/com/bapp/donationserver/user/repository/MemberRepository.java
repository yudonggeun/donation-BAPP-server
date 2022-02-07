package com.bapp.donationserver.user.repository;

import com.bapp.donationserver.data.MemberInfo;

public interface MemberRepository {
    void save(MemberInfo memberInfo);
    void update(String email, MemberInfo memberInfo);
    void delete(String email);
    MemberInfo findByEmail(String email);
}

package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Slf4j
@Primary
@RequiredArgsConstructor
public class JPAMemberRepositoryImpl implements MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(Member member) {
        log.info("새로운 멤버 등록 class={}", member);
        em.persist(member);
    }

    @Override
    public void update(String email, MemberDto memberInfo) {
        log.info("멤버 정보 수정 ={}", memberInfo);
        Member member = em.find(Member.class, email);
        member.setDto(memberInfo);
    }

    @Override
    public void delete(String email) {
        Member member = em.find(Member.class, email);
        em.remove(member);
    }

    @Override
    public Member findByEmail(String email) {
        log.info("멤버 조회={}", email);
        return em.find(Member.class, email);
    }

}

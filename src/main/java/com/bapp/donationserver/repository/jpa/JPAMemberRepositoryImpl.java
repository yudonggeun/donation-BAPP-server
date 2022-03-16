package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.DonatedCampaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.exception.IllegalUserDataException;
import com.bapp.donationserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public void update(Member member) {
        log.info("멤버 정보 수정 ={}",member);
        em.merge(member);
    }

    @Override
    public void delete(Member member) {
        em.remove(member);
    }

    @Override
    public Member findByEmail(String email) {
        log.info("멤버 조회={}", email);
        Member member = em.createQuery("select m from Member m join fetch m.wallet where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();

        return member;
    }

    @Override
    public List<DonatedCampaign> getMyDonationList(Member member) {
        em.persist(member);//수정이 필요해 보임
        return member.getDonatedCampaigns();
    }

}

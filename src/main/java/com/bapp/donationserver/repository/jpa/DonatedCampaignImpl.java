package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.DonatedCampaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.repository.DonatedCampaignRepository;
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
public class DonatedCampaignImpl implements DonatedCampaignRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void addDonatedCampaign(DonatedCampaign donatedCampaign) {
        em.merge(donatedCampaign);
    }

    @Override
    public List<DonatedCampaign> getMyDonationList(Member member) {

        String query = "select d from DonatedCampaign d join fetch d.campaign where d.member = :member_email";

        return em.createQuery(query, DonatedCampaign.class)
                .setParameter("member_email", member)
                .getResultList();
    }

}

package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.repository.CampaignRepository;
import com.bapp.donationserver.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class JPACampaignRepository implements CampaignRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(Campaign campaign) {
        em.persist(campaign);
    }

    @Override
    public void update(String campaignId, Campaign updateCampaign) {
        Campaign campaign = em.find(Campaign.class, campaignId);
        em.remove(campaign);
        em.persist(updateCampaign);
    }

    @Override
    public void delete(String campaignId) {
        Campaign campaign = em.find(Campaign.class, campaignId);
        em.remove(campaign);
    }

    @Override
    public Campaign findById(String campaignId) {
        return em.find(Campaign.class, campaignId);
    }

    @Override
    public List<Campaign> findAll() {
        return em.createQuery("select i from i Campaign").getResultList();
    }

    @Override
    public List<Campaign> findCampaignListByCondition(CampaignSearchCondition condition) {

        log.info("조건={}", condition);
        List<Campaign> campaigns = em.createQuery("select i from i Campaign").getResultList();
        
        /*//조건 필터
        for (String s : db.keySet()) {
            Campaign target = db.get(s);

            if(condition == null)
                continue;

            if(condition.getMemberType() == MemberType.USER && !target.getIsAccepted())
                continue;

            if ((condition.getCharityName() != null) && (!target.getCharityName().contains(condition.getCharityName())))
                continue;

            if ((condition.getSubject() != null) && (!target.getCampaignName().contains(condition.getSubject())))
                continue;

            if (condition.getCategories() != null
                    && !(target.getCategories() == null)
                    && !target.getCategories().containsAll(condition.getCategories())
            )
                continue;

            list.add(target);
        }*/
        //범위 확인
        if (campaigns.size() - 1 < condition.getStartIndex()) {
            log.info("검색 조건에 부합하는 캠페인이 없습니다.");
        }
        log.info("campaignRepository result={}", campaigns);
        return campaigns.subList(condition.getStartIndex(),
                (campaigns.size() <= condition.getEndIndex() ? campaigns.size() : condition.getEndIndex() + 1)
        );
    }
}

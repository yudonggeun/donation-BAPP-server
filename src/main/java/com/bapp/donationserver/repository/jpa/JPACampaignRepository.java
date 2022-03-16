package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.repository.CampaignRepository;
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
    public void update(Campaign campaign) {
        em.merge(campaign);
    }

    @Override
    public void delete(Campaign campaign) {
        em.remove(campaign);
    }

    @Override
    public Campaign findById(Long campaignId) {
        return em.find(Campaign.class, campaignId);
    }

    @Override
    public List<Campaign> findAll() {
        return em.createQuery("select c from Campaign as c", Campaign.class).getResultList();
    }

    @Override
    public List<Campaign> findCampaignListByCondition(CampaignSearchCondition condition) {

        //query dsl 을 통해서 조건 필터 완성하기 지금은 맛보기로만 코딩해놓자.
        log.info("조건={}", condition);
        String query = "select c from Campaign as c ";

        List<Campaign> campaigns = em.createQuery(query, Campaign.class)
                .setFirstResult(condition.getStartIndex())
                .setMaxResults(condition.getMaxResult())
                .getResultList();

        log.info("campaignRepository result={}", campaigns);
        return campaigns;
    }
}

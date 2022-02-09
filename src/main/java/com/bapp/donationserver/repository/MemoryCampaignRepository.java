package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Repository
public class MemoryCampaignRepository implements CampaignRepository{

    private final Map<String , Campaign> db = new HashMap<>();

    @Override
    public void save(Campaign campaignInfo) {
        campaignInfo.setCampaignId(UUID.randomUUID().toString());
        db.put(campaignInfo.getCampaignId(), campaignInfo);
    }

    @Override
    public void update(String campaignId, Campaign updateCampaignInfo) {
        db.replace(campaignId, updateCampaignInfo);
    }

    @Override
    public void delete(String campaignId) {
        db.remove(campaignId);
    }

    @Override
    public Campaign findById(String campaignId) {
        return db.get(campaignId);
    }

    @Override
    public List<Campaign> findAll() {
        return null;
    }

    @Override
    public List<Campaign> findCampaignListByCondition(CampaignSearchCondition condition) {
        List<Campaign> list = new ArrayList<>();

        log.info("조건={}", condition);
        for (String s : db.keySet()) {
            Campaign target = db.get(s);

            if(condition == null)
                continue;

            if ((condition.getCharityName() != null) && (!target.getCharityName().contains(condition.getCharityName())))
                continue;

            if ((condition.getSubject() != null) && (!target.getSubject().contains(condition.getSubject())))
                continue;

            if (condition.getCategories() != null
                    && !(target.getCategories() == null)
                    && !target.getCategories().containsAll(condition.getCategories())
            )
                continue;

            list.add(target);
        }
        log.info("campaignRepository result={}", list);
        return list;
    }

    @PostConstruct
    public void init(){

        addSampleCampaign("아동");
        addSampleCampaign("청년");
    }

    private void addSampleCampaign(String subject) {
        Campaign campaignInfo = new Campaign();

        campaignInfo.setCampaignId(UUID.randomUUID().toString());
        campaignInfo.setSubject(subject);
        campaignInfo.setCharityName("몬스터주식회사");
        campaignInfo.setDeadline(LocalDate.now());
        campaignInfo.setCurrentAmount(1000L);
        campaignInfo.setGoalAmount(1000000L);
        List<String> list = new ArrayList<>();
        list.add("아동");
        list.add("불우이웃");
        campaignInfo.setCategories(list);
        campaignInfo.setCoverImagePath("/path/child");
        campaignInfo.setDetailImagePath("/path/detail/child");

        db.put(campaignInfo.getCampaignId(), campaignInfo);
        log.info("save campaign={}", campaignInfo);
    }
}

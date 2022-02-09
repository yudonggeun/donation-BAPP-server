package com.bapp.donationserver.user.repository;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.CampaignSearchCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Repository
public class MemoryCampaignRepository implements CampaignRepository{

    private final Map<String , CampaignInfo> db = new HashMap<>();

    @Override
    public void save(CampaignInfo campaignInfo) {
        campaignInfo.setCampaignId(UUID.randomUUID().toString());
        db.put(campaignInfo.getCampaignId(), campaignInfo);
    }

    @Override
    public void update(String campaignId, CampaignInfo updateCampaignInfo) {
        db.replace(campaignId, updateCampaignInfo);
    }

    @Override
    public void delete(String campaignId) {
        db.remove(campaignId);
    }

    @Override
    public CampaignInfo findById(String campaignId) {
        return db.get(campaignId);
    }

    @Override
    public List<CampaignInfo> findAll() {
        return null;
    }

    @Override
    public List<CampaignInfo> findCampaignListByCondition(CampaignSearchCondition condition) {
        List<CampaignInfo> list = new ArrayList<>();

        for (String s : db.keySet()) {
            CampaignInfo target = db.get(s);

            if(condition == null)
                continue;

            if ((condition.getCharityName() != null) && (!target.getCharityName().contains(condition.getCharityName())))
                continue;

            if ((condition.getSubject() != null) && (!target.getSubject().contains(condition.getSubject())))
                continue;

            if ((condition.getCategories() != null) && (!target.getCategories().containsAll(condition.getCategories())))
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
        CampaignInfo campaignInfo = new CampaignInfo();

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

package com.bapp.donationserver.repository.memory;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.MemberType;
import com.bapp.donationserver.repository.CampaignRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemoryCampaignRepository implements CampaignRepository {

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
        //조건 필터
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
        }
        //범위 확인
        if (list.size() - 1 < condition.getStartIndex()) {
            log.info("검색 조건에 부합하는 캠페인이 없습니다.");
        }
        log.info("campaignRepository result={}", list);
        return list.subList(condition.getStartIndex(),
                (list.size() <= condition.getEndIndex() ? list.size() : condition.getEndIndex() + 1)
        );
    }
}

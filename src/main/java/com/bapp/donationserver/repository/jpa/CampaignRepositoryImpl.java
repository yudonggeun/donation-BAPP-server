package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.CampaignSearchCondition;
import com.bapp.donationserver.data.Category;
import com.bapp.donationserver.data.CategoryInfo;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.repository.custom.CustomCampaignRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public class CampaignRepositoryImpl implements CustomCampaignRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void update(Campaign campaign, List<String> categories) {

        //영속성 컨테이너 등록
        em.merge(campaign);

        //카테고리 수정 사항 없을 경우 종료
        if (categories == null)
            return;

        //모든 카테고리 항목 조회
        List<Category> allCategories = em.createQuery("select c from Category c", Category.class).getResultList();

        //해당 켐페인의 현재 카테고리 정보 조회
        String query = "select i from CategoryInfo i join fetch i.category where i.campaign = :campaign";
        List<CategoryInfo> updateCategoryList = em.createQuery(query, CategoryInfo.class)
                .setParameter("campaign", campaign)
                .getResultList();

        //수정시 제거하는 카테고리 항목 제거
        updateCategoryList.forEach(categoryInfo -> {
            String target = categoryInfo.getCategory().getName();
            if (!categories.contains(target)) {//카테고리 제거
                em.remove(categoryInfo);
            } else {
                categories.remove(target);
            }
        });

        //수정시 추가하는 카테고리 추가
        categories.forEach(stringCategory -> allCategories.forEach(category -> {
            if (category.getName().equals(stringCategory)) {
                CategoryInfo categoryInfo = new CategoryInfo();
                categoryInfo.setCampaign(campaign);
                categoryInfo.setCategory(category);

                em.persist(categoryInfo);
                updateCategoryList.add(categoryInfo);
            }
        }));

        //수정된 카테고리 목록 연결
        campaign.setCategories(updateCategoryList);
    }

    @Override
    public List<Campaign> findCampaignListByCondition(CampaignSearchCondition condition) {

        log.info("조건={}", condition);
        StringBuilder query = new StringBuilder("select c from Campaign c left join c.categories");
        List<String> whereQuery = new ArrayList<>();

        //where 조건 검사
        if (condition.getMemberType() != MemberType.ADMIN) {
            whereQuery.add("c.isAccepted = true");
        }
        if (condition.getCharityName() != null) {
            whereQuery.add("c.charityName like :charityName");
        }
        if (condition.getSubject() != null) {
            whereQuery.add("c.campaignName like :campaignName");
        }
        StringBuilder categoryCondition;
        if (condition.getCategories() != null && condition.getCategories().size() > 0) {
            List<String> categories = condition.getCategories();

            categoryCondition = new StringBuilder("c.id in (select i.campaign.id from CategoryInfo i where i.category.name like :category0");

            for (int i = 1; i < categories.size(); i++) {
                categoryCondition.append(" or i.category.name like :category").append(i);
            }

            categoryCondition.append(")");

            whereQuery.add(categoryCondition.toString());
        }

        //where 조건 and 연결
        if (whereQuery.size() > 0) {
            query.append(" where ").append(whereQuery.get(0)).append(" ");
            for (int i = 1; i < whereQuery.size(); i++) {
                query.append("and ").append(whereQuery.get(i)).append(" ");
            }
        }

        log.info("condition query = {}", query);


        TypedQuery<Campaign> typedQuery = em.createQuery(query.toString(), Campaign.class);

        //파라미터 적용
        if (condition.getCharityName() != null)
            typedQuery.setParameter("charityName", "%" + condition.getCharityName() + "%");

        if (condition.getSubject() != null)
            typedQuery.setParameter("campaignName", "%" + condition.getSubject() + "%");

        if (condition.getCategories() != null && condition.getCategories().size() > 0)
            IntStream.range(0, condition.getCategories().size()).forEach(i -> typedQuery.setParameter("category" + i, condition.getCategories().get(i)));

        //범위 설정 및 실행
        List<Campaign> campaigns = typedQuery
                .setFirstResult(condition.getStartIndex())
                .setMaxResults(condition.getMaxResult())
                .getResultList();

        log.info("campaignRepository result={}", campaigns);
        return campaigns;
    }
}

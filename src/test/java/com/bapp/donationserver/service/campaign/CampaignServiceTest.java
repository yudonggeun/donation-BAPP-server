package com.bapp.donationserver.service.campaign;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.type.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@Slf4j
@TestInstance(PER_CLASS)
class CampaignServiceTest {

    @Autowired CampaignService campaignService;

    CampaignFullDto dto;
    Long id;

    @BeforeAll
    void testCampaignEntityPutDB(){
        dto = sampleCampaignData();
        log.info("켐페인 등록");
        campaignService.registerCampaign(dto);
        List<CampaignSimpleDto> list = campaignService.checkCampaignList(new CampaignSearchConditionDto(), MemberType.ADMIN);
        list.forEach(campaignSimpleDto -> {
            if( dto.getCampaignName().equals(campaignSimpleDto.getSubject())){
                id = campaignSimpleDto.getCampaignId();
            }
        });
        log.info("저장된 켐페인 = {}", id);
    }

    @DisplayName("켐페인 정보 수정 시 변경 완료 확인 테스트")
    @Test
    void modifyCampaign() {
        CampaignFullDto changeData = new CampaignFullDto();
        changeData.setCampaignName("변경 after");
        changeData.setCharityName("변경단체 after");
        changeData.setDeadline(LocalDate.of(2222, 12, 12));
        changeData.setGoalAmount(10L);
        changeData.setCoverImagePath("change path by a after");
        changeData.setDetailImagePath("detail Iamge path after");
        changeData.setCurrentAmount(-1L);
        changeData.setCategories(modifyTestCategoryList());

        campaignService.modifyCampaign(id, changeData);
        Campaign find = campaignService.getDetailsOfCampaign(id);

        assertThat(find.getWallet().getAmount()).isNotSameAs(changeData.getCurrentAmount());
        assertThat(find.getCampaignName()).isEqualTo(changeData.getCampaignName());
        assertThat(find.getCharityName()).isEqualTo(changeData.getCharityName());
        assertThat(find.getDeadline()).isEqualTo(changeData.getDeadline());
        assertThat(find.getGoalAmount()).isEqualTo(changeData.getGoalAmount());
        assertThat(find.getCoverImagePath()).isEqualTo(changeData.getCoverImagePath());
        assertThat(find.getDetailImagePath()).isEqualTo(changeData.getDetailImagePath());
        find.getCategories().forEach(categoryInfo ->
                assertThat(modifyTestCategoryList()).contains(categoryInfo.getCategory().getName()));
    }

    private List<String> modifyTestCategoryList() {
        List<String> changeCategories = new ArrayList<>();
        changeCategories.add("아동");
        return changeCategories;
    }

    @DisplayName("정확한 켐페인 조회 테스트")
    @Test
    void getDetailsOfCampaign() {

        Campaign find = campaignService.getDetailsOfCampaign(id);

        assertThat(find).isNotNull();

        assertThat(find.getWallet().getAmount()).isNotSameAs(dto.getCurrentAmount());
        assertThat(find.getCampaignName()).isEqualTo(dto.getCampaignName());
        assertThat(find.getCharityName()).isEqualTo(dto.getCharityName());
        assertThat(find.getDeadline()).isEqualTo(dto.getDeadline());
        assertThat(find.getGoalAmount()).isEqualTo(dto.getGoalAmount());
        assertThat(find.getCoverImagePath()).isEqualTo(dto.getCoverImagePath());
        assertThat(find.getDetailImagePath()).isEqualTo(dto.getDetailImagePath());
        find.getCategories().forEach(categoryInfo ->
                assertThat(dto.getCategories()).contains(categoryInfo.getCategory().getName()));
    }

    @DisplayName("켐페인 승인 상태 변경 테스트")
    @Test
    void acceptCampaign() {

        //승인 상태 거부로 변경
        Boolean status = campaignService.acceptCampaign(id, false);
        assertThat(campaignService.getDetailsOfCampaign(id).getIsAccepted()).isSameAs(status);

        //승인 상태 허용으로 변경
        status = campaignService.acceptCampaign(id, true);
        assertThat(campaignService.getDetailsOfCampaign(id).getIsAccepted()).isSameAs(status);

    }

    private CampaignFullDto sampleCampaignData() {
        CampaignFullDto dto = new CampaignFullDto();
        dto.setCampaignName("변경");
        dto.setCharityName("변경단체");
        dto.setDeadline(LocalDate.of(2000, 1, 1));
        dto.setGoalAmount(0L);
        dto.setCoverImagePath("change path by a");
        dto.setDetailImagePath("detail Iamge path");
        List<String> changeCategories = modifyTestCategoryList();
        changeCategories.add("청년");
        dto.setCategories(changeCategories);
        return dto;
    }
}
package com.bapp.donationserver.entity;

import com.bapp.donationserver.data.dto.CampaignFullDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
 */
@Entity
@Getter
@Setter
public class Campaign {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "NAME")
    private String campaignName;
    @Column(name = "CHARITY")
    private String charityName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DEADLINE")
    private LocalDate deadline;
    @Column(name = "GOAL_AMOUNT")
    private Long goalAmount;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<CategoryInfo> categories = new ArrayList<>();
    @Column(name = "COVER_IMAGE")
    private String coverImagePath;
    @Column(name = "DETAIL_IMAGE")
    private String detailImagePath;
    @Column(name = "REVIEW_IMAGE")
    private String reviewImagePath;
    @Column(name = "ACCEPT")
    private Boolean isAccepted;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;

    public Campaign() {
        this.isAccepted = false;
    }

    public void setCampaignFullDto(CampaignFullDto dto) {
        setCampaignName(dto.getSubject());
        setCharityName(dto.getCharityName());
        setDeadline(dto.getDeadline());
        setGoalAmount(dto.getGoalAmount());
        setCoverImagePath(dto.getCoverImagePath());
        setDetailImagePath(dto.getDetailImagePath());
        setReviewImagePath(dto.getReviewImagePath());
        setDescription(dto.getDescription());
    }
}

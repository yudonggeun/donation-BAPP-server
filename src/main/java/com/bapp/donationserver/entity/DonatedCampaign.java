package com.bapp.donationserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class DonatedCampaign {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    private Campaign campaign;

    public static DonatedCampaign create(Member member, Campaign campaign){
        DonatedCampaign donatedCampaign = new DonatedCampaign();
        donatedCampaign.setMember(member);
        donatedCampaign.setCampaign(campaign);

        return donatedCampaign;
    }
}
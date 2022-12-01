package com.bapp.donationserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class CategoryInfo {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumn(name = "CAMPAIGN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Campaign campaign;
    @JoinColumn(name = "CATEGORY_NAME")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    public CategoryInfo() {
    }

    public CategoryInfo(Campaign campaign, Category category) {
        this.campaign = campaign;
        this.category = category;
    }

}

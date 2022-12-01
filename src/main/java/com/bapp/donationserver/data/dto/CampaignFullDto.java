package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.entity.Campaign;
import lombok.*;

import java.util.List;

import static java.util.stream.Collectors.*;

@Getter @Setter
@ToString @EqualsAndHashCode
public class CampaignFullDto extends CampaignSimpleDto{
    private List<String> categories;
    private String detailImagePath;
    private String reviewImagePath;

    public CampaignFullDto(Campaign entity) {
        super(entity);
        this.categories = entity.getCategories().stream().map(info -> info.getCategory().getName()).collect(toList());
        this.detailImagePath = entity.getDetailImagePath();
        this.reviewImagePath = entity.getReviewImagePath();
    }
}

package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.CategoryDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "CATEGORY")
public class Category {
    @Id
    private String name;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<CategoryInfo> campaigns;

    public void setDto(CategoryDto dto) {
        setName(dto.getName());
    }
}

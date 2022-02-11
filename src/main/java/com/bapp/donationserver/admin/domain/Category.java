package com.bapp.donationserver.admin.domain;

import com.bapp.donationserver.admin.dto.CategoryDto;
import lombok.Data;

@Data
public class Category {

    private String name;
    private Integer like;

    public CategoryDto getDto() {
        return new CategoryDto(name);
    }

    public void setDto(CategoryDto dto) {
        setName(dto.getName());
    }
}

package com.bapp.donationserver.admin.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private String name;

    public CategoryDto(String name) {
        this.name = name;
    }
}

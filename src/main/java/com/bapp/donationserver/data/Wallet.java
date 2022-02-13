package com.bapp.donationserver.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Wallet {
    @Id
    private String Id;
    private Long amount;
}

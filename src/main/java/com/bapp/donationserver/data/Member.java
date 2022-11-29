package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.type.MemberType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private MemberType memberType;
    @Column(name = "USERNAME")
    private String name;
    @Column(name = "PHONE")
    private String phoneNumber;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NICKNAME")
    private String nickname;
    @Column(name = "PROFILE_IMAGE")
    private String profilePhotoName;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<InterestCampaign> interestCampaigns = new ArrayList<>();//기부 단체의 경우에 관심 켐패인이 아닌 등록한 켐패인 리스트
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<DonatedCampaign> donatedCampaigns = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;

    public Member(){}

    public Member(String email, Wallet wallet, MemberType memberType) {
        this.email = email;
        this.wallet = wallet;
        this.memberType = memberType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfilePhotoName(String profilePhotoName) {
        this.profilePhotoName = profilePhotoName;
    }

    public void setDto(MemberDto data) {
        if(data.getName() != null) setName(data.getName());
        if(data.getPhoneNumber() != null) setPhoneNumber(data.getPhoneNumber());
        if(data.getPassword() != null) setPassword(data.getPassword());
        if(data.getNickname() != null) setNickname(data.getNickname());
        if(data.getProfilePhotoName() != null) setProfilePhotoName(data.getProfilePhotoName());
    }
}

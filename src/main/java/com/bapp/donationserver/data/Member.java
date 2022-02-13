package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.MemberDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    public void setDto(MemberDto data) {
        setMemberType(data.getMemberType());
        setName(data.getName());
        setPhoneNumber(data.getPhoneNumber());
        setEmail(data.getEmail());
        setPassword(data.getPassword());
        setNickname(data.getNickname());
        setProfilePhotoName(data.getProfilePhotoName());
    }

    public MemberDto getDto() {
        MemberDto myPageDto = new MemberDto();
        myPageDto.setMemberType(getMemberType());
        myPageDto.setName(getName());
        myPageDto.setPhoneNumber(getPhoneNumber());
        myPageDto.setEmail(getEmail());
        myPageDto.setPassword(getPassword());
        myPageDto.setNickname(getNickname());
        myPageDto.setProfilePhotoName(getProfilePhotoName());
        myPageDto.setPointAmount(getWallet().getAmount());

        return myPageDto;
    }
}

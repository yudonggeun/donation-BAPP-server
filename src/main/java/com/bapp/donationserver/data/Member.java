package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.MemberDto;
import lombok.Data;
//import com.sun.istack.NotNull;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
import java.util.List;

//@Entity
@Data
public class Member {
//    @Id
//    @NotNull
    private String email;
//    @NotNull
    private MemberType memberType;
    private String name;
    private String phoneNumber;
//    @NotNull
    private String password;
    private String nickname;
    private String profilePhotoName;
    private List<Campaign> interestCampaigns;//기부 단체의 경우에 관심 켐패인이 아닌 등록한 켐패인 리스트
    private List<Campaign> donatedCampaigns;
//    @OneToOne
//    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    public void setMyPageDto(MemberDto data) {
        setMemberType(data.getMemberType());
        setName(data.getName());
        setPhoneNumber(data.getPhoneNumber());
        setEmail(data.getEmail());
        setPassword(data.getPassword());
        setNickname(data.getNickname());
        setProfilePhotoName(data.getProfilePhotoName());
    }

    public MemberDto getMyPageDto() {
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

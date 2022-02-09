package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.MyPageDto;
import lombok.Data;

import java.util.List;

@Data
public class Member {
    private MemberType memberType;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String nickname;
    private String profilePhotoName;
    private List<Campaign> interestCampaigns;//기부 단체의 경우에 관심 켐패인이 아닌 등록한 켐패인 리스트
    private List<Campaign> donatedCampaigns;

    public void setMyPageDto(MyPageDto data){
        setMemberType(data.getMemberType());
        setName(data.getName());
        setPhoneNumber(data.getPhoneNumber());
        setEmail(data.getEmail());
        setPassword(data.getPassword());
        setNickname(data.getNickname());
        setProfilePhotoName(data.getProfilePhotoName());
    }

    public MyPageDto getMyPageDto(){
        MyPageDto myPageDto = new MyPageDto();
        myPageDto.setMemberType(getMemberType());
        myPageDto.setName(getName());
        myPageDto.setPhoneNumber(getPhoneNumber());
        myPageDto.setEmail(getEmail());
        myPageDto.setPassword(getPassword());
        myPageDto.setNickname(getNickname());
        myPageDto.setProfilePhotoName(getProfilePhotoName());

        return myPageDto;
    }
}

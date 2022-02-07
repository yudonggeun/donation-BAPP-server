package com.bapp.donationserver.data;

import com.bapp.donationserver.data.url.MyPageDto;
import lombok.Data;

@Data
public class MemberInfo {
    private MemberType memberType;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String nickname;
    private String profilePhotoName;

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

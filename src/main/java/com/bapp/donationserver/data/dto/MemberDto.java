package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.data.type.MemberType;
import lombok.Data;

/**
 * 클라이언트 전달 정보 : 회원 유형
 * 일반 사용자 : 이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진
 * 단체 사용자 : 단체이름, 번호, 이메일, 패스워드, 닉네임, 프로필 사진 -> 승인 후 가입
 */
@Data
public class MemberDto {

    private MemberType memberType;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String nickname;
    private String profilePhotoName;
    private String walletId;
    private Long pointAmount;

}

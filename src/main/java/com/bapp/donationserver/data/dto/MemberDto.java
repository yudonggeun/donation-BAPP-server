package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.exception.IllegalUserDataException;
import lombok.Data;

import java.util.regex.Pattern;

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


    public static void checkValidation(MemberDto memberDto){

        StringBuilder sb = new StringBuilder();
        boolean isNotValid = false;

        Pattern emailPattern = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");
        Pattern passwordPattern = Pattern.compile("^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");
        Pattern phoneNumberPattern = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
        Pattern filePattern = Pattern.compile("^\\S+.(?i)(jpg|jpeg|png|bmp|gif)$");

        String email = memberDto.getEmail();
        String password = memberDto.getPassword();
        String phoneNumber = memberDto.getPhoneNumber();
        String profilePhotoName = memberDto.getProfilePhotoName();
        String nickname = memberDto.getNickname();

        if(email != null && !emailPattern.matcher(email).find()){
            isNotValid = true;
            sb.append("올바른 이메일 형식이 아닙니다.\n");
        }
        if(phoneNumber != null && !phoneNumberPattern.matcher(phoneNumber).find()){
            isNotValid = true;
            sb.append("올바른 전화번호 형식이 아닙니다.\n");
        }
        if(password != null && !passwordPattern.matcher(password).find()){
            isNotValid = true;
            sb.append("올바른 패스워드 형식이 아닙니다. : 숫자, 문자, 특수문자 포함 8~15자리 이내로 설정해주세요.\n");
        }
        if(profilePhotoName != null && !filePattern.matcher(profilePhotoName).find()){
            isNotValid = true;
            sb.append("올바른 파일 형식이 아닙니다. : 프로필 파일을 다시 전송해주세요. : jpg|jpeg|png|bmp|gif 지원\n");
        }

        if(isNotValid){
            throw new IllegalUserDataException(sb.toString());
        }
    }
}

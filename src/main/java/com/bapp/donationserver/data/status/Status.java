package com.bapp.donationserver.data.status;

import lombok.Data;

@Data
public class Status {
    public int code;
    public String message;

    public static Status successStatus(){
        Status status = new Status();
        status.setCode(200);
        status.setMessage("api 요청이 정상 처리 되었습니다.");

        return status;
    }

    public static Status failStatus(String msg){
        Status status = new Status();
        status.setCode(400);
        status.setMessage(msg);

        return status;
    }
}

package com.bapp.donationserver.data.status;

import lombok.Data;

@Data
public class Return {
    public int code;
    public String message;

    public static Return successStatus(){
        Return returns = new Return();
        returns.setCode(200);
        returns.setMessage("request success");

        return returns;
    }

    public static Return successStatusWithData(Object data){
        ReturnData returns = new ReturnData();
        returns.setCode(200);
        returns.setMessage("request success");
        returns.setData(data);
        return returns;
    }

    public static Return failStatus(String msg){
        Return returns = new Return();
        returns.setCode(400);
        returns.setMessage(msg);

        return returns;
    }
}

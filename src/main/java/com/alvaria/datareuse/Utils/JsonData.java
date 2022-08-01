package com.alvaria.datareuse.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JsonData {
    private int code;
    private Object data;
    private String msg;


    public JsonData(int code, Object data){
        this.code = code;
        this.data = data;
    }
    public JsonData(String msg, Object data){
        this.msg = msg;
        this.data = data;
    }
    public static JsonData buildSuccess(Object data){
        return new JsonData(0, data);
    }

    public static JsonData buildError(String msg, Object data){
        return new JsonData(msg, data);
    }
}

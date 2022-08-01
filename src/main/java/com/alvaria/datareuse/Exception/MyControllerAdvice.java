package com.alvaria.datareuse.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ResponseBody
@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> errorHandle(Exception e){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",-1);
        map.put("msg",e.getMessage());
        return map;
    }
}

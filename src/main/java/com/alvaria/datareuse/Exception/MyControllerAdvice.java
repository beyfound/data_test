package com.alvaria.datareuse.Exception;

import com.alvaria.datareuse.entity.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ResponseBody
@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseResult errorHandle(Exception e) {
        return new ResponseResult(-1, null, e.getMessage());
    }
}

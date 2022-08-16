package com.alvaria.datareuse.Interceptor;

import com.alvaria.datareuse.Utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        Map<String, Object> map = new HashMap<>();
        if (token != null) {
            try {
                JwtUtil.verify(token);
                return true;
            } catch (Exception e) {
                map.put("msg", "token无效");
            }
        } else {
            map.put("msg", "token为空");
        }
        map.put("status", false);
        //错误信息响应到前台
        response.setContentType("application/json;charset=utf-8");
        //.getWriter().print(new Json.toJson(map));
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return false;
    }
}

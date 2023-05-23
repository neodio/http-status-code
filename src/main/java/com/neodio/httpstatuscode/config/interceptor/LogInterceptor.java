package com.neodio.httpstatuscode.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod method = (HandlerMethod) handler;

                log.info("[LogInterceptor] URL: {}", method.getMethodAnnotation(RequestMapping.class).path());
            }
        } catch (Exception e) {
            log.warn("[LogInterceptor] Failed get path: {}", e.getMessage());
        }

        return true;
    }
}

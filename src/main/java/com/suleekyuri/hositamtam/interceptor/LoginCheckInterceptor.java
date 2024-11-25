package com.suleekyuri.hositamtam.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 인증된 사용자 ID가 없으면 로그인 페이지로 리다이렉트
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // principal이 "anonymousUser" 또는 null인 경우에만 로그인 페이지로 리다이렉트
        if (principal == null || principal.equals("anonymousUser")) {
            response.sendRedirect("/login");
            return false;
        }

        return true; // 로그인한 사용자는 계속 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // post 처리, 필요시
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 요청 완료 후 처리, 필요시
    }
}

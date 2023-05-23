package com.neodio.httpstatuscode.config.web;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.neodio.httpstatuscode.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * web config
 */
@Configuration
public class WebConfig {

    /**
     * Lucy XSS Filter 를 적용한다.<br>
     * 모든 요청에 대해서 Escape 처리하도록 설정되어 있음.<br>
     * STG profile 이 active 되어 있을 경우만 동작하도록 설정되어 있음.<br>
     *
     * @return {@link FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean xssEscapeServletFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssEscapeServletFilter());
        registrationBean.setOrder(0);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    /**
     * XSS Filter 등록
     *
     * @return {@link FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean wmpXssFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}

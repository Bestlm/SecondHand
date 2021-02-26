package com.wu.config;


import com.wu.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new UserFilter());
        //在访问购物车下的  订单下面  用户信息和 用户地址下的资源的时候 会执行拦截器
        filterRegistrationBean.addUrlPatterns("/cart/*","/orders/*","/user/userInfo","/userAddress/*");
        return filterRegistrationBean;
    }

}

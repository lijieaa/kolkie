package com.jianpanmao;

import com.jianpanmao.common.filter.ModifyParametersFilter;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }


    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new ModifyParametersFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}

package com.twkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@MapperScan("com.twkj.dao")
@SpringBootApplication
public class TwkpApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TwkpApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TwkpApplication.class, args);
    }
}

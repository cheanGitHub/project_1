package com.cc.springmvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableWebMvc
@Configuration
@ComponentScan(
//        basePackageClasses = MyWebMvcConfig.class
        basePackages = "com.cc.user,com.cc.mybatis"
)
@EnableLoadTimeWeaving
public class MyWebMvcConfig {
    static {
        System.out.println("AAAA-----------------MyWebMvcConfig");
    }
    // WebMvcConfigurerAdapter
    // DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport

    public static void main(String[] args) {
        System.out.println(new File("E:\\Program Files (x86)\\Heroes & Generals\\live\\_packed\\Characters").exists());
        System.out.println(new File("E:\\Program Files (x86)\\Heroes & Generals\\live\\_packed\\Characters").delete());
    }
}

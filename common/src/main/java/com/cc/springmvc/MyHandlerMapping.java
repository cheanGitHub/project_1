package com.cc.springmvc;

import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;

public class MyHandlerMapping extends AbstractHandlerMapping {

    public MyHandlerMapping(){
        System.out.println("MyHandlerMapping init");
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        return null;
    }
}

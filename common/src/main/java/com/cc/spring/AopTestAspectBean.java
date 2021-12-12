package com.cc.spring;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class AopTestAspectBean {

    @Pointcut("execution(* *.work(..))")
    public void pc() {
    }

    @Before("pc()")
    public void bf() {
        System.out.println("bf");
    }

    @After("pc()")
    public void af() {
        System.out.println("af");
    }

    @Around("pc()")
    public Object ar(ProceedingJoinPoint p) {
        System.out.println("ar-start");
        Object proceed = null;
        try {
            proceed = p.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("ar-end");
        return proceed;
    }
}

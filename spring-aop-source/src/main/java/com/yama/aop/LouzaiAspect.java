package com.yama.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @description:添加 LouzaiAspect 切面
 * @date: 2022年10月22日 周六 22:04
 * @author: yama946
 */
@Aspect
@Component
public class LouzaiAspect {

    @Pointcut("execution(* com.yama.aop.Louzai.everyDay())")
    private void myPointCut() {
    }

    // 前置通知
    @Before("myPointCut()")
    public void myBefore() {
        System.out.println("吃饭");
    }

    // 后置通知
    @AfterReturning(value = "myPointCut()")
    public void myAfterReturning() {
        System.out.println("打豆豆。。。");
    }
}

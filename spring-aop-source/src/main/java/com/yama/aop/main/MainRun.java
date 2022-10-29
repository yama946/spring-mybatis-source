package com.yama.aop.main;

import com.yama.aop.Louzai;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @date: 2022年10月22日 周六 22:17
 * @author: yama946
 */
public class MainRun {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:bean.xml");
        Louzai louzai = (Louzai) applicationContext.getBean("louzai");
        louzai.everyDay();
    }
}

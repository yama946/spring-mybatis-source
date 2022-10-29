package com.yama.source.main;

import com.yama.source.config.MainConfig1;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description: 通过包扫描的方式注入容器中的Bean
 * @date: 2022年10月17日 周一 9:59
 * @author: yama946
 */
public class ComponentScanDemo02 {
    public static void main(String[] args) {
        //获取ioc容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig1.class);
        //获取容器中所有组件的名
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name:names){
            System.out.println("组件名："+name);
        }
    }
}

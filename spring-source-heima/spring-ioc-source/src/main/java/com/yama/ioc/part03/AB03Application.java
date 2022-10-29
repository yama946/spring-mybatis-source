package com.yama.ioc.part03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @description: 第三讲：bean生命周期讲解
 * @date: 2022年10月24日 周一 22:28
 * @author: yama946
 */
@SpringBootApplication
public class AB03Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AB03Application.class, args);
        //为了演示bean销毁阶段，关闭容器操作
        context.close();
    }
}

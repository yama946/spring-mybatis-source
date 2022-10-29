package com.yama.ioc.part01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

/**
 * part01：主要将Bean的两个接口
 * @description: BeanFactory 与 ApplicationContext 的区别
 * @date: 2022年10月23日 周日 19:46
 * @author: yama946
 */
@SpringBootApplication
public class AB01Application {
    private static final Logger log = LoggerFactory.getLogger(AB01Application.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {

        //返回的是一个spring容器对象
        ConfigurableApplicationContext context = SpringApplication.run(AB01Application.class, args);
        /*
            1. 到底什么是 BeanFactory
                - 它是 ApplicationContext 的父接口
                - 它才是 Spring 的核心容器, 主要的 ApplicationContext 实现都【组合】了它的功能
                - 容器中存在一个BeanFactory对象，用来调用器方法拓展功能。
         */
//        context.getBean("aaa");
        System.out.println(context);
        /*
            2. BeanFactory 能干点啥
                - 表面上只有 getBean
                - 实际上控制反转、基本的依赖注入、直至 Bean 的生命周期的各种功能, 都由它的实现类提供
                - 实现类继承接口实现，除此之外实现类还实现了其他接口，拓展功能。
         */
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
                .forEach(e -> {
                    System.out.println(e.getKey() + "=" + e.getValue());
        });

        /*
            3. ApplicationContext 比 BeanFactory 多点啥
                ApplicationContext主要实现其他四个接口，会比Beanfactory功能要多

         */
        //1、国际化能力，能获取翻译语言，翻译从本地文件中获取----->MessageSource
        //应用：将网页翻译成请求需要的语言页面，请求头中会携带语言类型。
        /**
         * # 设置不使用默认文件，否则使用默认message.properties无法达到翻译效果
         * spring.messages.use-code-as-default-message=true
         */
        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
        System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

        //2、获取资源进行解析------>ResourcePatternResolver接口
        //Resource是spring对获取到资源的抽象
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        //3、获取环境信息----->EnvironmentCapable接口
        System.out.println(context.getEnvironment().getProperty("java_home"));
        System.out.println(context.getEnvironment().getProperty("server.port"));

        //4、发布事件，可以用于解耦------>ApplicationEventPublisher接口
        context.publishEvent(new UserRegisteredEvent(context));
        /**
         * EventSend组件只有被容器注册成功后，其事件才会被发送。
         */
        context.getBean(EventSend.class).register();

        /*
            4. 学到了什么
                a. BeanFactory 与 ApplicationContext 并不仅仅是简单接口继承的关系, ApplicationContext 组合并扩展了 BeanFactory 的功能
                b. 又新学一种代码之间解耦途径
            练习：完成用户注册与发送短信之间的解耦, 用事件方式、和 AOP 方式分别实现
         */
    }
}

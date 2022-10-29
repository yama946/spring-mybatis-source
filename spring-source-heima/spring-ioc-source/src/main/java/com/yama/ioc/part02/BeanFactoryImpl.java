package com.yama.ioc.part02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: BeanFactory的实现类
 * @date: 2022年10月23日 周日 21:18
 * @author: yama946
 */
public class BeanFactoryImpl {
    public static void main(String[] args) {
        //1.BeanFactory中最主要的实现类
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // bean 的定义（class, scope, 初始化, 销毁）,实现类根据bean的定义创建对象

        //1).使用BeanDefinitionBuilder定义一个Bean，设置(可以设置单例等)后调用getBeanDefinition()生成Bean的定义
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(Config.class)
                .setScope("singleton")
                .setPrimary(true)
                .getBeanDefinition();
        //2).将Bean的定义交给BeanFactory实现类进行注册到容器
        beanFactory.registerBeanDefinition("config",beanDefinition);

        //3).可以发现，此时只有Config注册了，Config中注解标注的并没有注册，这说明注册Bean定义并不能解析注解
//        for (String bean : beanFactory.getBeanDefinitionNames()) {
//            System.out.println(bean);//config
//        }

        // 2.给BeanFactory添加一些后处理器，将后处理器添加到容器中
        // 后处理对象，拓展了BeanFactory的功能，能够解析注解配置对象到容器中
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        /**
         * config
         * org.springframework.context.annotation.internalConfigurationAnnotationProcessor Configuration注解处理器
         * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
         * org.springframework.context.annotation.internalCommonAnnotationProcessor
         * org.springframework.context.event.internalEventListenerProcessor
         * org.springframework.context.event.internalEventListenerFactory
         */
//        for (String bean : beanFactory.getBeanDefinitionNames()) {
//            System.out.println(bean);
//        }

        //2.1、获取IOC容器中的所有后处理器，是一个Map集合，其中key是名字，value是后处理器对象
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessors = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        //2.2、从集合中获取后处理对象，并运行后处理器对象
        beanFactoryPostProcessors.values().stream().forEach(beanFactoryPostProcessor -> {
            //运行后处理器
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
        /**
         * config
         * org.springframework.context.annotation.internalConfigurationAnnotationProcessor
         * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
         * org.springframework.context.annotation.internalCommonAnnotationProcessor
         * org.springframework.context.event.internalEventListenerProcessor
         * org.springframework.context.event.internalEventListenerFactory
         * bean1
         * bean2
         */
        for (String bean : beanFactory.getBeanDefinitionNames()) {
            System.out.println(bean);
        }
        /**
         * 此时：通过Bean1对象获取Bean2,返回为null
         * 说明：此时@AutoWired注解未起作用。
         * 后处理器：
         *      BeanFactory后处理：上面可以看出可以让Configration注解起作用
         *      Bean后处理：要想@Autowired起作用，就要使用Bean后处理器
         */
//        System.out.println(beanFactory.getBean(Bean1.class).getBean2());//注入容器后启用后处理器，没用

        //3.Bean 后处理器, 针对 bean 的生命周期的各个阶段提供扩展, 例如 @Autowired @Resource ...
        //后处理器，前面我们都给放到容器中了，只需要获取执行即可
        Map<String, BeanPostProcessor> beanPostProcessors = beanFactory.getBeansOfType(BeanPostProcessor.class);
        System.out.println(beanPostProcessors.values().size());
        //3.2 获取对象，并执行

        beanFactory.preInstantiateSingletons(); // 准备好所有单例

        beanPostProcessors.values().stream()
                /*转换后处理器的顺序*/
                .sorted(beanFactory.getDependencyComparator())
                .forEach(beanPostProcessor -> {
            System.out.println(">>>>" + beanPostProcessor);
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        });
        //3.3 再次获取，
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());

        /*
            学到了什么:
            a. beanFactory 不会做的事-------》ApplicationContext都做了，因此我们可以直接使用
                   1. 不会主动调用 BeanFactory 后处理器
                   2. 不会主动添加 Bean 后处理器
                   3. 不会主动初始化单例
                   4. 不会解析beanFactory 还不会解析 ${ } 与 #{ }
            b. bean 后处理器会有排序的逻辑
         */

        System.out.println("Common:" + (Ordered.LOWEST_PRECEDENCE - 3));
        System.out.println("Autowired:" + (Ordered.LOWEST_PRECEDENCE - 2));

    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

        @Bean
        public Bean3 bean3() {
            return new Bean3();
        }

        @Bean
        public Bean4 bean4() {
            return new Bean4();
        }
    }

    interface Inter {

    }

    static class Bean3 implements Inter {

    }

    static class Bean4 implements Inter {

    }

    static class Bean1 {
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean1() {
            log.debug("构造 Bean1()");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

        /**
         * 添加两个注解，最终注入是那个对象呢？
         * 是：bean3
         * 因为解析Autowired的后处理器优先加载AutowiredAnnotationBeanPostProcessor
         * 解析@Resources的后处理器CommonAnnotationBeanPostProcessor之后才加载
         */
        @Autowired
        @Resource(name = "bean4")
        private Inter bean3;

        public Inter getInter() {
            return bean3;
        }
    }

    static class Bean2 {
        private static final Logger log = LoggerFactory.getLogger(Bean2.class);

        public Bean2() {
            log.debug("构造 Bean2()");
        }
    }
}

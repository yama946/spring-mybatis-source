package com.yama.source.def;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: @Import注册组件的第三种方式
 * @date: 2022年10月17日 周一 13:31
 * @author: yama946
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param importingClassMetadata 标注@Import注解的类的所有注解信息
     * @param registry  把所有需要注册到容器中的类，通过registry.registerBeanDefinition方法
     *                  进行手动注册到容器中。
     *                  BeanDefinitionRegistry该类对象能够直接操作ioc容器进行获取容器信息，并注册组件。
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        /**
         * beanName：自定义bean名
         */
        registry.registerBeanDefinition("color",new RootBeanDefinition("com.yama.source.color.Color"));
    }
}

package com.yama.source.config;

import com.yama.source.def.MyTypeFilter;
import com.yama.source.entity.Person;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Controller;

/**
 * 通过配置文件的方式来注入容器
 * @ComponentScan(basePackages = "com.yama.source")
 *              ||
 * <context:component-scan base-package="com.yama.source" use-default-filters="false" />
 * 属性：
 *      basePackages：指定要扫描的包。
 *      excludeFilters:指定扫描的时候依照什么规则排除组件
 *              @Filter：注解
 *                  type：表示以什么规则过滤。
 *                  classes：指定需要过滤的注解类型
 *      includeFilters：指定扫描的时候只需要包含那些组件
 *      useDefaultFilters：要想includeFilters属性起作用，需要配置该属性
 *                         作用：false表示禁用掉默认的过滤规则。
 * 详解过滤类型：
 *      type：表示以什么规则过滤。
 *      FilterType.ANNOTATION：按照注解，常用
 *      FilterType.ASSIGNABLE_TYPE:按照给定的类型，常用
 *      FilterType.ASPECTJ：按照aspectj表达式，基本不会使用
 *      FilterType.REGEX:按照正则表达式,不常用
 *      FilterType.CUSTOM:依照自定义规则，需要实现TypeFilter
 *
 *
 * @description: spring配置类的方式配置
 * @date: 2022年10月17日 周一 9:33
 * @author: yama946
 */
//配置类<==>配置文件
@Configuration  //告诉spring这是一个配置类
@ComponentScan(basePackages = "com.yama.source",includeFilters = {
        @Filter(type = FilterType.ANNOTATION,classes = {Controller.class}),
        @Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {Person.class}),
        @Filter(type = FilterType.CUSTOM,classes = MyTypeFilter.class)},useDefaultFilters = false)
public class MainConfig1 {

    /**
     * 1、@Bean注解详解：
     *      作用：给spring容器中注册一个Bean,类型为方法返回值的类型。
     *      属性：
     *     value：
     *       Bean的名，默认为用方法名作为默认名
     *      可以通过@Bean中的value，name属性进行指定名字。
     *
     * 2、@Scope注解详解：
     *      作用：调整作用域
     *      属性值：
     *      prototype:多实例的：ioc容器启动时，并不会去调用方法创建对象放到ioc容器中。
     *                          每次获取的时候才会调用方法创建对象
     *      singleton:单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。
     *                              以后每次获取就是直接从ioc容器(map.get())中获取。
     *      request: 同一请求创建一个实例
     *      session： 同一个session创建一个实例
     *3、@Lazy注解详解：
     *      作用：懒加载
     *      默认：
     *          针对单实例bean：默认在容器启动的创建对象。
     *      启动懒加载后：
     *          容器启动不创建对象。第一个使用（获取）Bean创建对象，并初始化。
     *
     *
     * @return
     */
    @Scope("singleton")
    @Lazy
    @Bean("person")
    public Person initPerson(){
        //测试创建对象是单实例的
        System.out.println("给容器中添加Person.......");
        Person person = new Person();
        person.setName("孙悟空");
        person.setAge(500);
        return person;
    }
}

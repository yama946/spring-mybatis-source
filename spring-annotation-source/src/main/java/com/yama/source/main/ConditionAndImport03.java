package com.yama.source.main;

import com.yama.source.config.MainConfig1;
import com.yama.source.config.MainConfig2;
import com.yama.source.entity.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * @description:
 * @date: 2022年10月17日 周一 11:06
 * @author: yama946
 */
public class ConditionAndImport03 {
    public static void main(String[] args) {
        method2();
    }

    public static void method2(){
        //获取ioc容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        //获取所有组件名
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name:names){
            System.out.println(name);
        }
    }


    public static void method1(){
        //获取ioc容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        //通过容器获取当前系统环境变量信息
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Map<String, Object> systemProperties = environment.getSystemProperties();
        for (Map.Entry<String,Object> entry:systemProperties.entrySet()){
            System.out.println("系统变量属性："+entry.getKey()+"----->属性值："+entry.getValue());
        }
        //依照数据类型获取组件名
        String[] names = applicationContext.getBeanNamesForType(Person.class);
        for (String name:names){
            System.out.println(name);
        }
        //从ioc容器中获取组件，根据类型
        Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
        System.out.println(persons);
    }
}

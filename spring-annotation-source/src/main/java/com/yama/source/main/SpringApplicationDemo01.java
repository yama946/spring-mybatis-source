package com.yama.source.main;

import com.yama.source.config.MainConfig1;
import com.yama.source.entity.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @date: 2022年10月17日 周一 9:38
 * @author: yama946
 */
public class SpringApplicationDemo01 {

    public static void main(String[] args) {
        //配置文件的方式注入对象
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        //配置类的方式获取对象
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig1.class);
        Person person = (Person)applicationContext.getBean("person");
        System.out.println(person);
    }
}

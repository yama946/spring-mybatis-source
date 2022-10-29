package com.yama.ioc.part03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 当前bean注入到容器中，bean中代码执行顺序
 * 1.LifeCycleBean()：构造方法首先执行
 * 2.autowire(@Value("${JAVA_HOME}") String home)：注入方法再执行
 * 3.    @PostConstruct
 *     public void init()：再执行初始化完成方法
 * 4.    @PreDestroy：Bean销毁时，执行该注解标注的方法
 * @description: 向容器中注入bean
 * @date: 2022年10月24日 周一 22:30
 * @author: yama946
 */
@Component
public class LifeCycleBean {
    private static final Logger log = LoggerFactory.getLogger(LifeCycleBean.class);

    public LifeCycleBean() {
        log.debug("构造");
    }

    /**
     * @Autowired注解作用在方法上：
     *      作用：
     *          加在方法上，那么spring容器在加载完之后，会去从容器中找对应方法中参数类型的对象，
     *          并且将值赋值它，然后自动执行一遍方法；
     * @Value("${JAVA_HOME}") String home
     * 解析：
     *      对于要注入的参数，如果是对象，会从容器中查找并注入，但是对于String类型不会
     *      所以要使用@Value注解从配置文件或者环境变量中获取注入
     * @param home
     */
    @Autowired
    public void autowire(@Value("${JAVA_HOME}") String home) {
        log.debug("依赖注入: {}", home);
    }

    /**
     * bean初始化方法
     */
    @PostConstruct
    public void init() {
        log.debug("初始化");
    }

    /**
     * bean销毁前方法
     */
    @PreDestroy
    public void destroy() {
        log.debug("销毁");
    }
}

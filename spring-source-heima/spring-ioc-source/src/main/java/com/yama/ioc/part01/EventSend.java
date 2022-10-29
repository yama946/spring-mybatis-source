package com.yama.ioc.part01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 需要注入容器生效
 * @description: 发送事件，相当于发送消息
 * @date: 2022年10月23日 周日 20:52
 * @author: yama946
 */
@Component
@Slf4j
public class EventSend {
    /**
     * 可以直接获取容器，也可以直接注入事件发布器类型的Bean
     */
    @Autowired
    private ApplicationEventPublisher context;

    /**
     *  发送事件
     */
    public void register() {
        log.debug("用户注册");
//        log.debug("发送短息");//直接写死注册后操作
        //发送事件，让事件处理器决定，如何进行下一步操作，即使以后下一步操作改变，当前代码也无需修改。
        context.publishEvent(new UserRegisteredEvent(this));
    }
}

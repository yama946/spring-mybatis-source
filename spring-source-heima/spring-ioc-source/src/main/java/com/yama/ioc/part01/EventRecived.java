package com.yama.ioc.part01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 需要注入容器生效
 * @description: 事件接收器，相当于消费者
 * @date: 2022年10月23日 周日 20:53
 * @author: yama946
 */
@Component
@Slf4j
public class EventRecived {
    /**
     * @EventListener 表明此方法是一个事件处理器
     * @param event 处理指定的事件类型
     */
    @EventListener
    public void aaa(UserRegisteredEvent event) {
        log.debug("{}", event);
        log.debug("发送短信");
    }
}

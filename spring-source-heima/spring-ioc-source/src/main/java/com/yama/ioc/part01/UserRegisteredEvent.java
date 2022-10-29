package com.yama.ioc.part01;

import org.springframework.context.ApplicationEvent;

/**
 * 定义事件类型，需要继承ApplicationEvent
 */
public class UserRegisteredEvent extends ApplicationEvent {
        public UserRegisteredEvent(Object source) {
            super(source);
        }
}

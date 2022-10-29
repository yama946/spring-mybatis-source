package com.yama.source.def;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @description: 判断是否为Windows系统
 * @date: 2022年10月17日 周一 11:34
 * @author: yama946
 */
public class WindowsConditon implements Condition {
    /**
     *
     * @param context 判断条件能使用的上下文环境
     * @param metadata  标注的注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String property = environment.getProperty("os.name");
        if (property.contains("Windows")){
            return true;
        }
        return false;
    }
}

package com.yama.source.def;

import com.yama.source.color.Blue;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义逻辑返回需要注册的组件
 * @description: 实现ImportSelector接口，返回要注册的类的全类名
 * @date: 2022年10月17日 周一 13:13
 * @author: yama946
 */
public class MyImportSelector implements ImportSelector {
    /**
     * @param importingClassMetadata    标注@Import注解的类的所有注解信息
     * @return      要导入组件的全类名数组
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        return new String[0];
        return new String[]{"com.yama.source.color.Blue"};
    }
}

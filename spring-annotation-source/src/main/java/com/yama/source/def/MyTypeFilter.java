package com.yama.source.def;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 使用位置：
 *
 * @ComponentScan(basePackages = "com.yama.source",includeFilters = {
 *         @Filter(type = FilterType.CUSTOM,classes = MyTypeFilter.class)},useDefaultFilters = false)
 *
 * @description: MyTypeFilter  自定义ComponentScan的过滤规则
 * @date: 2022/10/17 10:31
 * @author: yama946
 */
public class MyTypeFilter implements TypeFilter {
    /**
     * @param metadataReader    读取当前正在扫描类的信息
     * @param metadataReaderFactory 可以获取到其他任何类的信息
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前扫描的类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类的资源信息（类的路径等信息）
        Resource resource = metadataReader.getResource();

        String className = classMetadata.getClassName();
        System.out.println("----->"+className);

        return false;
    }
}

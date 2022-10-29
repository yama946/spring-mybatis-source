package com.yama.source.config;

import com.yama.source.color.Red;
import com.yama.source.def.LinuxCondition;
import com.yama.source.def.MyImportBeanDefinitionRegistrar;
import com.yama.source.def.MyImportSelector;
import com.yama.source.def.WindowsConditon;
import com.yama.source.entity.Person;
import org.springframework.context.annotation.*;

/**
 * 容器中注册组件的方式：
 *
 *      1.【适用自定义类】包扫描+组件标注注解(@Component、@Controller、@Service、@Repository)
 *      2.【适用导入的第三方组件】@Bean注解,默认为方法名，比如：person
 *      3.【适用导入的第三方组件】@Imort:容器中导入组件，默认id为全类名，比如：com.yama.source.entity.Person
 *
 * @description: Conditional注解和Import注解
 * @date: 2022年10月17日 周一 11:05
 * @author: yama946
 */

/**
 * 1、@Import(value = {Red.class, Blue.class})注解详解
 *          作用：向容器中自动导入组件，组件名为全类名。示例：com.yama.source.color.Red
 *          用法1
 *              1).直接指定包注册的类，@Import(Red.class)：使用指定类的无参构造器创建对象，注入到容器中
 *          用法2：
 *                 ImportSelector:返回需要注册的主键的全类名数组，进行注册。
 *              1).自定义类实现ImportSelector接口。
 *              2).@Import({MyImportSelector.class}):将自定义类作为@Import的参数传递，获取方法返回值
 *                                                  会将返回的全类名进行注册到ioc容器中。
 *                 默认id，也是全类名
 *          用法3：
 *                 ImportBeanDefinitionRegistrar：
 *              1):自定义类实现ImportBeanDefinitionRegistrar接口
 *              2):在@Import中导入自定义类，@Import({MyImportBeanDefinitionRegistrar.class})
 */
@Configuration
@Conditional(LinuxCondition.class) //作用在类上：类中组件的同一设置。满足条件，这个类中的配置的所有Bean注册才能生效。
@Import({Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

    /**
     * 测试要求：
     *      如果是window系统，给容器中注册bill
     *      如果是Linux系统，给容器中注册linus
     *
     * 1、@Conditional
     *      作用在类上：类中组件的同一设置。满足条件，这个类中的配置的所有Bean注册才能生效。
     *      作用在方法上：按照一定的条件进行判断，满足条件给容器中注册Bean
     *      属性参数值为：
     *          继承public interface Condition的类。
     *
     * 2、知识点：
     *      设置运行时变量：
     *          vm options中设置程序运行时变量：
     *                  -D为固定前缀：
     *                  示例：更改环境变量中os.name的值
     *                      -Dos.name=linux
     *
     */

    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public Person person01(){
        return new Person("linus",43);
    }

    @Conditional(WindowsConditon.class)
    @Bean("bill")
    public Person person02(){
        return new Person("Bill",63);
    }
}

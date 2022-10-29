package com.yama.aop;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @date: 2022年10月22日 周六 21:17
 * @author: yama946
 */
@Data
@Service
public class Louzai  {
    public void everyDay() {
        System.out.println("睡觉");
    }
}

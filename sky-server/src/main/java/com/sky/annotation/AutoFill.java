package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动填充
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)  // 表示在运行时生效
public @interface AutoFill {
    OperationType value();  // 操作类型
}

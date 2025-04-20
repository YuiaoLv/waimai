package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自动填充切面
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    /*
     * 前置通知
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段数据填充...");

        // 获取当前被拦截的方法对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获得注解
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
        //获得操作类型
        OperationType operationType = autoFill.value();
        //获得参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object object = args[0];// 仅获取第一个作为对象
        //准备要赋值的数据
        Long currentId = BaseContext.getCurrentId();
        LocalDateTime now = LocalDateTime.now();
        switch (operationType) {
            case INSERT:
                try {
                    Method insertMethod = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                    insertMethod.invoke(object, now);
                    Method insertMethod2 = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                    insertMethod2.invoke(object, currentId);
                    Method insertMethod3 = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    insertMethod3.invoke(object, now);
                    Method insertMethod4 = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                    insertMethod4.invoke(object, currentId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case UPDATE:
                try {
                    Method updateMethod = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    updateMethod.invoke(object, now);
                    Method updateMethod2 = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                    updateMethod2.invoke(object, currentId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}


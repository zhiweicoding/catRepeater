package io.github.zhiweicoding.csw.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @Created by zhiwei on 2022/4/6.
 */
@Aspect
@Slf4j
public class ObserverAop {

    @Pointcut("@annotation(io.github.zhiweicoding.csw.aop.AopObserver)")
    public void annotationPointCut() {
    }

    @After("annotationPoinCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AopObserver action = method.getAnnotation(AopObserver.class);
        System.out.println("注解式拦截 " + action.name());
    }

}

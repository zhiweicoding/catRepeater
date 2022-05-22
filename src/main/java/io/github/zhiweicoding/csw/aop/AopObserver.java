package io.github.zhiweicoding.csw.aop;

import java.lang.annotation.*;

/**
 * @Created by zhiwei on 2022/4/6.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopObserver {
    String name();
}

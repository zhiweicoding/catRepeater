package io.github.zhiweicoding.csw.support;

import java.util.concurrent.CompletableFuture;

/**
 * @Created by zhiwei on 2022/3/29.
 */
public interface Support<T> {

    /**
     * 初始化
     */
    void init(T t);

    default boolean match(String name) {
        return false;
    }

    /**
     * 开始操作
     *
     * @return
     */
    default boolean start() {
        return false;
    }

    default boolean stop() {
        return false;
    }

    default boolean clear() {
        return false;
    }
}

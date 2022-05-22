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

    /**
     * 开始操作
     *
     * @return
     */
    boolean start();

    boolean stop();

    boolean clear();
}

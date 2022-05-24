package io.github.zhiweicoding.csw.support.es;

/**
 * @Created by zhiwei on 2022/5/22.
 */
public interface EsNeedJob<T> {

    default void save(T t) {
    }

    default void update(T t) {
    }

    default void delete(T t) {
    }

    void bulk(T t);
}

package io.github.zhiweicoding.csw.support.es;

/**
 * @Created by zhiwei on 2022/5/22.
 */
public interface EsNeedJob<T> {

    void save(T t);

    void update(T t);

    void delete(T t);
}

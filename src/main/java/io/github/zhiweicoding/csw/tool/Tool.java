package io.github.zhiweicoding.csw.tool;

/**
 * @program: smartBear
 * @author: zhiwei
 * @email: diaozhiwei2k@163.com
 * @create: 2020-02-07 13:50
 * @description:
 **/
public interface Tool<T, V> {

    T work(V t);

}

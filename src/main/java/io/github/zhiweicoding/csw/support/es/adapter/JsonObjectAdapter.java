package io.github.zhiweicoding.csw.support.es.adapter;

import com.alibaba.fastjson2.JSON;

import java.util.Map;

/**
 * @author by diaozhiwei on 2022/05/24.
 * @email diaozhiwei2k@163.com
 */
public class JsonObjectAdapter implements Adapter<Map<String, Object>> {

    public static final String name = "jsonObject";

    @Override
    public Map<String, Object> trans(String val) {
        return JSON.parseObject(val);
    }
}

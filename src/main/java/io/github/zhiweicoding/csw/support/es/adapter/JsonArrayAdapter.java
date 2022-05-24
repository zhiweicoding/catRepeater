package io.github.zhiweicoding.csw.support.es.adapter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author by diaozhiwei on 2022/05/24.
 * @email diaozhiwei2k@163.com
 */
public class JsonArrayAdapter implements Adapter<List<Map<String, Object>>> {

    public static final String name = "jsonArray";

    @Override
    public List<Map<String, Object>> trans(String val) {
        List<Map<String, Object>> array = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(val);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            array.add(jsonObject);
        }
        return array;
    }
}

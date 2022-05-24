package io.github.zhiweicoding.csw.support.es.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.zhiweicoding.csw.support.es.EsNeedJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Created by zhiwei on 2022/5/22.
 */
@Slf4j
@Component
public class EsOrderSupport implements EsNeedJob {

    public static final String IMPORT_KEY = "specialParamsIsArrayTypeNeedToAssembly";

    @Override
    public boolean match(String tableName) {
        return tableName.equals("t_order");
    }

    @Override
    public boolean filter(CanalEntry.EventType type) {
        return true;
    }

    @Override
    public void assembly(Map<String, Object> params, String key, String val) {
        if (!val.startsWith("[")) {
            EsNeedJob.super.assembly(params, key, val);
        }
//        JSONArray objects = JSON.parseArray(val);
//        List<Map<String, Object>> array = new ArrayList<>();
//        for (int i = 0; i < objects.size(); i++) {
//            JSONObject obj = objects.getJSONObject(i);
//            Map<String, Object> map = new HashMap<>();
//
//            for (String valKey : obj.keySet()) {
//                map.put(key + "_" + valKey, obj.get(valKey));
//            }
//
//            array.add(map);
//        }
//
//        params.put(IMPORT_KEY, array);
    }
}

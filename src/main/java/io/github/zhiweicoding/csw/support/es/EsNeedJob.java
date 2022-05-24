package io.github.zhiweicoding.csw.support.es;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.zhiweicoding.csw.support.Support;
import io.github.zhiweicoding.csw.support.es.bean.EsCanalEntity;

import java.util.List;
import java.util.Map;

/**
 * @Created by zhiwei on 2022/5/22.
 */
public interface EsNeedJob extends Support<List<EsCanalEntity>> {

    boolean match(String tableName);

    boolean filter(CanalEntry.EventType type);

    default void assembly(Map<String, Object> params, String key, String val) {
        JSONObject jsonObject = JSON.parseObject(val);
        for (String valKey : jsonObject.keySet()) {
            params.put(key + "_" + valKey, jsonObject.get(valKey));
        }
    }
}

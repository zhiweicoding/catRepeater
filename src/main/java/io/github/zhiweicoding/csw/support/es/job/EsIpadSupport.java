package io.github.zhiweicoding.csw.support.es.job;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.zhiweicoding.csw.support.es.EsNeedJob;
import io.github.zhiweicoding.csw.support.es.bean.EsCanalEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Created by zhiwei on 2022/5/22.
 */
@Slf4j
@Component
public class EsIpadSupport implements EsNeedJob {

    @Override
    public boolean match(String tableName) {
        return tableName.equals("t_ipad");
    }

    @Override
    public boolean filter(CanalEntry.EventType type) {
        return type != CanalEntry.EventType.DELETE;
    }

}

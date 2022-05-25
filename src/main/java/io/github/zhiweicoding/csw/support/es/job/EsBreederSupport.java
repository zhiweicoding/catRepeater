package io.github.zhiweicoding.csw.support.es.job;

import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.zhiweicoding.csw.support.es.EsNeedJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Created by zhiwei on 2022/5/22.
 */
@Slf4j
@Component
public class EsBreederSupport implements EsNeedJob {

    @Override
    public boolean match(String tableName) {
        return tableName.equals("t_breeder");
    }

    @Override
    public boolean filter(CanalEntry.EventType type) {
        return true;
    }


    @Override
    public void assembly(Map<String, Object> params, String key, String val) {
        params.put(key, val);
    }
}

package io.github.zhiweicoding.csw.support.es.job;

import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.zhiweicoding.csw.support.es.EsNeedJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Created by zhiwei on 2022/5/22.
 */
@Slf4j
@Component
public class EsProtectSupport implements EsNeedJob {

    @Override
    public boolean match(String tableName) {
        return tableName.equals("t_protect");
    }

    @Override
    public boolean filter(CanalEntry.EventType type) {
        return true;
    }

}

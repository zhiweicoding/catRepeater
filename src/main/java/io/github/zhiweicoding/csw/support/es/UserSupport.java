package io.github.zhiweicoding.csw.support.es;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.protobuf.InvalidProtocolBufferException;
import io.github.zhiweicoding.csw.dao.es.UserMapper;
import io.github.zhiweicoding.csw.models.UserBean;
import io.github.zhiweicoding.csw.support.Support;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by zhiwei on 2022/5/22.
 */
@Slf4j
@Component
public class UserSupport implements Support<List<CanalEntry.Entry>>, EsNeedJob<List<List<CanalEntry.Column>>> {

    @Autowired
    private UserMapper userMapper;

    public static final String tableName = "t_user";

    @Override
    public void init(List<CanalEntry.Entry> entries) {
        List<List<CanalEntry.Column>> dels = new ArrayList<>();
        List<List<CanalEntry.Column>> saves = new ArrayList<>();
        List<List<CanalEntry.Column>> mods = new ArrayList<>();
        try {
            for (CanalEntry.Entry e : entries) {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(e.getStoreValue());
                CanalEntry.EventType eventType = rowChange.getEventType();
                List<CanalEntry.RowData> rList = rowChange.getRowDatasList();

                for (CanalEntry.RowData rowData : rList) {
                    switch (eventType) {
                        case DELETE:
                            dels.add(rowData.getBeforeColumnsList());
                            break;
                        case INSERT:
                            saves.add(rowData.getAfterColumnsList());
                            break;
                        case UPDATE:
                            mods.add(rowData.getAfterColumnsList());
                            break;
                        default:
                            break;
                    }
                }
            }
            if (dels.size() > 0) {
                delete(dels);
            }
            if (saves.size() > 0) {
                save(saves);
            }
            if (mods.size() > 0) {
                update(mods);
            }
        } catch (InvalidProtocolBufferException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean match(String name) {
        return tableName.equals(name);
    }

    @Override
    public void save(List<List<CanalEntry.Column>> columns) {
        List<UserBean> us = new ArrayList<>();
        for (List<CanalEntry.Column> cs : columns) {
            UserBean u = new UserBean();
            for (CanalEntry.Column c : cs) {
                String name = c.getName();
                String value = c.getValue();
                boolean updated = c.getUpdated();
                boolean isNull = c.getIsNull();

            }
        }
    }

    @Override
    public void update(List<List<CanalEntry.Column>> columns) {

    }

    @Override
    public void delete(List<List<CanalEntry.Column>> columns) {

    }
}

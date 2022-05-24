package io.github.zhiweicoding.csw.support.es;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.protobuf.InvalidProtocolBufferException;
import io.github.zhiweicoding.csw.dao.es.UserMapper;
import io.github.zhiweicoding.csw.models.UserBean;
import io.github.zhiweicoding.csw.support.Support;
import io.github.zhiweicoding.csw.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Created by zhiwei on 2022/5/22.
 */
@Slf4j
@Component
public class UserSupport implements Support<List<CanalEntry.Entry>>, EsNeedJob<List<List<CanalEntry.Column>>> {

    @Autowired
    private UserMapper userMapper;

    public static final String tableName = "t_user";

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString().replace("-", ""));
    }

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
    public void save(List<List<CanalEntry.Column>> columns) {
        List<UserBean> us = new ArrayList<>();
        for (List<CanalEntry.Column> cs : columns) {
            UserBean u = new UserBean();
            Method[] methods = u.getClass().getMethods();
            us.add(u);
            for (CanalEntry.Column c : cs) {
                String name = c.getName();
                String value = c.getValue();
                boolean updated = c.getUpdated();
                boolean isNull = c.getIsNull();
                if (!isNull) {
                    for (Method method : methods) {
                        String methodSetStr = method.getName().toLowerCase();
                        String getSetStr = ("set" + name.replace("_", "")).toLowerCase();
                        if (methodSetStr.equals(getSetStr)) {
                            try {
                                Class<?>[] parameterTypes = method.getParameterTypes();
                                Class<?> parameterType = parameterTypes[0];
                                if (parameterType.getName().contains("java.lang.String")) {
                                    method.invoke(u, value);
                                } else if (parameterType.getName().contains("java.lang.Integer")) {
                                    method.invoke(u, Integer.parseInt(value));
                                } else if (parameterType.getName().contains("java.lang.Long")) {
                                    method.invoke(u, Long.parseLong(value));
                                } else if (parameterType.getName().contains("java.time.LocalDateTime")) {
                                    LocalDateTime ldt = DateUtil.getLdt(value);
                                    method.invoke(u, ldt);
                                } else if (parameterType.getName().contains("java.lang.Double")) {
                                    method.invoke(u, Double.parseDouble(value));
                                } else if (parameterType.getName().contains("java.lang.Float")) {
                                    method.invoke(u, Float.parseFloat(value));
                                }
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                            break;
                        }
                    }
                }
            }
        }
        userMapper.insertBatch(us, tableName);
    }

    @Override
    public void update(List<List<CanalEntry.Column>> columns) {

    }

    @Override
    public void delete(List<List<CanalEntry.Column>> columns) {

    }
}

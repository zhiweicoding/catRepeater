package io.github.zhiweicoding.csw.support.es;

import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.zhiweicoding.csw.support.es.bean.EsCanalEntity;
import io.github.zhiweicoding.csw.support.es.job.EsOrderSupport;
import io.github.zhiweicoding.csw.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author by diaozhiwei on 2022/05/24.
 * @email diaozhiwei2k@163.com
 */
@Component
@Slf4j
public class EsManager {

    @Autowired
    private RestHighLevelClient client;

    private static List<EsNeedJob> jobs = new ArrayList<>();

    private static List<EsCanalEntity> rs = new ArrayList<>();

    public EsManager doBulk() {
        BulkRequest request = new BulkRequest();
        for (EsCanalEntity r : rs) {
            String id = r.getId();
            String tableName = r.getTableName();
            Map<String, Object> params = r.getParams();
            CanalEntry.EventType paramsEnum = r.getParamsEnum();
            switch (paramsEnum) {
                case DELETE:
                    DeleteRequest deleteRequest = new DeleteRequest(tableName)
                            .id(id);
                    request.add(deleteRequest);
                    break;
                case INSERT:
                    IndexRequest indexRequest = new IndexRequest(tableName)
                            .id(id)
                            .source(params);
                    request.add(indexRequest);
                    break;
                case UPDATE:
                    UpdateRequest updateRequest = new UpdateRequest(tableName, id)
                            .id(id)
                            .doc(params)
                            .docAsUpsert(true);
                    request.add(updateRequest);
                    break;
                default:
                    break;
            }
        }
        if (request.requests().size() > 0) {
            client.bulkAsync(request, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse response) {
                    if (response.hasFailures()) {
                        log.warn(response.buildFailureMessage());
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    log.error(e.getMessage(), e);
                }
            });
        }

        return this;
    }


    public EsManager adapter(List<CanalEntry.Entry> entries) throws Exception {
        for (CanalEntry.Entry entry : entries) {
            String tableName = entry.getHeader().getTableName();
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            CanalEntry.EventType eventType = rowChange.getEventType();
            List<CanalEntry.RowData> rList = rowChange.getRowDatasList();
            Optional<EsNeedJob> optionalEsNeedJob = jobs.stream().filter(bean -> bean.match(tableName)).findFirst();
            if (optionalEsNeedJob.isPresent()) {
                for (CanalEntry.RowData rowData : rList) {
                    EsCanalEntity e = new EsCanalEntity();
                    EsNeedJob esNeedJob = optionalEsNeedJob.get();
                    e.setJob(esNeedJob);
                    e.setParamsEnum(eventType);
                    e.setTableName(tableName);
                    if (esNeedJob.filter(eventType)) {
                        Map<String, Object> col = new HashMap<>();
                        switch (eventType) {
                            case DELETE:
                                col = getCol(rowData.getBeforeColumnsList(), esNeedJob);
                                break;
                            case INSERT:
                            case UPDATE:
                                col = getCol(rowData.getAfterColumnsList(), esNeedJob);
                                break;
                            default:
                                break;
                        }
                        e.setId(String.valueOf(col.get("_id")));
                        col.remove("_id");
                        e.setParams(col);
                        rs.add(e);
                    }
                }
            }
        }
        return this;
    }

    protected Map<String, Object> getCol(List<CanalEntry.Column> cs, EsNeedJob esNeedJob) {
        Map<String, Object> params = new HashMap<>();
        int i = 0;
        for (CanalEntry.Column c : cs) {
            String name = c.getName();
            String value = c.getValue();
            boolean isKey = c.getIsKey();
            String mysqlType = c.getMysqlType();
            boolean isNull = c.getIsNull();
            if (!isNull && !"".equals(value)) {
                if (mysqlType.contains("varchar") || mysqlType.contains("text") || mysqlType.contains("longtext")) {
                    if (value.contains("{") && value.contains("}")) {
                        esNeedJob.assembly(params, name, value);
                    } else {
                        params.put(name, value);
                    }

                } else if (mysqlType.contains("datetime")) {
                    params.put(name, DateUtil.getTimeStamp(value));
                } else if (mysqlType.contains("int")) {
                    params.put(name, Integer.parseInt(value));
                } else {
                    params.put(name, Float.parseFloat(value));
                }
                if (isKey && i == 0) {
                    params.put("_id", value);
                }
            }
            i++;
        }
        return params;
    }


    public void addJob(EsNeedJob job) {
        jobs.add(job);
    }

    public void clear() {
        rs.clear();
    }
}

package io.github.zhiweicoding.csw.support.es.bean;

import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.zhiweicoding.csw.support.es.EsNeedJob;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

/**
 * @author by diaozhiwei on 2022/05/24.
 * @email diaozhiwei2k@163.com
 */
public class EsCanalEntity {
    private String tableName;
    private String id;
    private Map<String, Object> params;

    private CanalEntry.EventType paramsEnum;

    private EsNeedJob job;

    public EsCanalEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EsCanalEntity that = (EsCanalEntity) o;

        return new EqualsBuilder().append(tableName, that.tableName).append(id, that.id).append(params, that.params).append(paramsEnum, that.paramsEnum).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tableName).append(id).append(params).append(paramsEnum).toHashCode();
    }

    @Override
    public String toString() {
        return "EsCanalEntity{" +
                "tableName='" + tableName + '\'' +
                ", id='" + id + '\'' +
                ", params=" + params +
                ", paramsEnum=" + paramsEnum +
                ", job=" + job.getClass().getSimpleName() +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public CanalEntry.EventType getParamsEnum() {
        return paramsEnum;
    }

    public void setParamsEnum(CanalEntry.EventType paramsEnum) {
        this.paramsEnum = paramsEnum;
    }

    public EsNeedJob getJob() {
        return job;
    }

    public void setJob(EsNeedJob job) {
        this.job = job;
    }
}

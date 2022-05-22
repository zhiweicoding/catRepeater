package io.github.zhiweicoding.csw;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import io.github.zhiweicoding.csw.dao.es.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Created by zhiwei on 2022/5/22.
 */
@Component
@Slf4j
public class CanalSupport implements CommandLineRunner {

    protected CanalConnector connector;

    @Autowired
    private UserMapper userMapper;

    protected static int batchSize = 1000;

    @PostConstruct
    public void prepare() {
        InetSocketAddress address = new InetSocketAddress(AddressUtils.getHostIp(), 11111);
        connector = CanalConnectors.newSingleConnector(address, "example", "", "");
        connector.connect();
        connector.subscribe("pet\\.t_order,pet\\.t_good,pet\\.t_user");
        connector.rollback();
    }

    @Override
    public void run(String... args) {
        try {
            //从线程池启动一个single线程
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<?> submit = executorService.submit(() -> {
                try {
                    while (true){
                        Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                        long batchId = message.getId();
                        int size = message.getEntries().size();
                        if (batchId == -1 || size == 0) {
                            Thread.sleep(500);
                        } else {
                            printEntry(message.getEntries());
                        }
                        connector.ack(batchId); // 提交确认
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            });
            submit.get();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<CanalEntry.Entry> entries) throws Exception {
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());

            CanalEntry.EventType eventType = rowChange.getEventType();
            System.out.printf("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s%n",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType);

            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------&gt; before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------&gt; after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}

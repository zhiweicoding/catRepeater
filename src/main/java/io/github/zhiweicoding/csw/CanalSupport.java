package io.github.zhiweicoding.csw;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import io.github.zhiweicoding.csw.support.es.*;
import io.github.zhiweicoding.csw.support.es.job.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.List;
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
    private EsCommonSupport esCommonSupport;
    @Autowired
    private EsGoodSupport esGoodSupport;
    @Autowired
    private EsProtectSupport esProtectSupport;
    @Autowired
    private EsIpadSupport esIpadSupport;
    @Autowired
    private EsManager esManager;
    @Autowired
    private EsOrderSupport esOrderSupport;
    @Value("${canalConfig.table}")
    private String canalTable;

    protected static int batchSize = 5000;

    @PostConstruct
    public void prepare() {
        InetSocketAddress address = new InetSocketAddress(AddressUtils.getHostIp(), 11111);
        connector = CanalConnectors.newSingleConnector(address, "example", "", "");
        connector.connect();
        connector.subscribe(canalTable);
        connector.rollback();

        esManager.addJob(esCommonSupport);
        esManager.addJob(esGoodSupport);
        esManager.addJob(esIpadSupport);
        esManager.addJob(esProtectSupport);
        esManager.addJob(esOrderSupport);
    }

    @Override
    public void run(String... args) {
        try {
            //从线程池启动一个single线程
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<?> submit = executorService.submit(() -> {
                try {
                    while (true) {
                        Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                        long batchId = message.getId();
                        int size = message.getEntries().size();
                        if (batchId == -1 || size == 0) {
                            Thread.sleep(800);
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

    private void printEntry(List<CanalEntry.Entry> entries) {
        try {
            esManager.adapter(entries).doBulk().clear();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}

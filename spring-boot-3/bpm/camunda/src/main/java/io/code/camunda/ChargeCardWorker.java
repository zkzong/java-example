package io.code.camunda;

import org.camunda.bpm.client.ExternalTaskClient;

import java.util.logging.Logger;

/**
 * @Author: zongz
 * @Date: 2026-04-01
 */
public class ChargeCardWorker {

    private final static Logger LOGGER = Logger.getLogger(ChargeCardWorker.class.getName());

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                // 长轮询超时
                .asyncResponseTimeout(10000)
                .build();

        // 订阅流程中指定的外部任务主题
        client.subscribe("charge-card")
                // 默认的锁定持续时间是20s，可以覆盖它
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {
                    // 把业务逻辑放在这里

                    // 获取流程变量
                    String item = externalTask.getVariable("item");
                    Long amount = externalTask.getVariable("amount");
                    LOGGER.info("Charging credit card with an amount of '"
                            + amount + "'€ for the item '" + item + "'...");
                    // 完成任务
                    externalTaskService.complete(externalTask);
                }).open();
    }
}

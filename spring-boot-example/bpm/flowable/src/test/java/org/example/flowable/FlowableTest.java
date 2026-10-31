package org.example.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.jupiter.api.Test;

/**
 * @Author: zongz
 * @Date: 2025-02-22
 */
public class FlowableTest {

    /***
     *
     * 部署流程到数据库中
     * 在非Spring环境下的应用
     */
    @Test
    public void deployFlow() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration();
        // 获取流程引擎对象
        ProcessEngine processEngine = cfg.buildProcessEngine();
    }

}

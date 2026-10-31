package org.example.xxljob.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 定时任务示例
 *
 * @Author: zongz
 * @Date: 2024-11-20
 */
@Component
public class DemoJobHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @XxlJob("demoJobHandler")
    public void execute() {
        String param = XxlJobHelper.getJobParam();
        XxlJobHelper.log("定时任务开始执行，参数：{}", param);
        XxlJobHelper.log("当前时间：{}", LocalDateTime.now().format(FORMATTER));
        XxlJobHelper.handleSuccess("定时任务执行完成");
    }
}

package io.code.sb3.mybatis.dbread.controller;

import io.code.sb3.mybatis.dbread.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zongz
 * @Date: 2025-03-01
 */
@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping("/processData")
    public String processData() {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            dataService.processData();
            stopWatch.stop();
            double totalTime = stopWatch.getTotalTime(TimeUnit.SECONDS);
            return "success : " + totalTime;
        } catch (Exception e) {
            return "failed";
        }
    }

    @RequestMapping("/processDataInParallel")
    public String processDataInParallel() {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            dataService.processDataInParallel();
            stopWatch.stop();
            double totalTime = stopWatch.getTotalTime(TimeUnit.SECONDS);
            return "success : " + totalTime;
        } catch (Exception e) {
            return "failed";
        }
    }

    @RequestMapping("/processDataInParallelCursor")
    public String processDataInParallelCursor() {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            dataService.processDataInParallelCursor();
            stopWatch.stop();
            double totalTime = stopWatch.getTotalTime(TimeUnit.SECONDS);
            return "success : " + totalTime;
        } catch (Exception e) {
            return "failed";
        }
    }

    @RequestMapping("/dynamicProcess")
    public String dynamicProcess() {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            dataService.dynamicProcess();
            stopWatch.stop();
            double totalTime = stopWatch.getTotalTime(TimeUnit.SECONDS);
            return "success : " + totalTime;
        } catch (Exception e) {
            return "failed";
        }
    }

}

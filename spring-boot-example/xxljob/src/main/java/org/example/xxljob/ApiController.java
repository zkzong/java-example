package org.example.xxljob;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 通过api接口操作任务
 *
 * @Author: zongz
 * @Date: 2024-11-21
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddress;

    @RequestMapping("/update")
    public String update() {

        // 登录
        HttpResponse httpResponse = HttpRequest.post(adminAddress + "/login")
                .form("userName", "admin")
                .form("password", "123456")
                .execute();
        String header = httpResponse.header("Set-Cookie");
        System.out.println(header);

        // pagelist
        String body = HttpRequest.post(adminAddress + "/jobinfo/pageList")
                // triggerStatus -1：全部，0：未运行，1：运行
                .form("jobGroup", 1)
                .form("triggerStatus", -1)
                .execute().body();
        System.out.println(body);

        String data = JSONUtil.parseObj(body).get("data").toString();
        JSONArray jsonArray = JSONUtil.parseArray(data);
        Object obj = jsonArray.get(0);
        Map map = JSONUtil.parseObj(obj);
        map.remove("addTime");
        map.remove("updateTime");
        map.remove("glueUpdatetime");
        map.put("scheduleConf", "0/15 0 0 * * ? *");
        body = HttpRequest.post(adminAddress + "/jobinfo/update")
                .form(map)
                .execute().body();
        System.out.println(body);

        return "success";
    }

}

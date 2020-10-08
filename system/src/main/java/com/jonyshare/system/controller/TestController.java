package com.jonyshare.system.controller;

import com.jonyshare.system.domain.Test;
import com.jonyshare.system.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangQiang
 * @date 2020/10/7-11:53
 */
@RestController
public class TestController {

    @Resource
    TestService testService;

    @RequestMapping("/test")
    public List<Test> test() {
        return testService.list();
    }
}

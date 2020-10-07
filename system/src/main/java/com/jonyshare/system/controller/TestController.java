package com.jonyshare.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangQiang
 * @date 2020/10/7-11:53
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test() {
        return "success";
    }
}

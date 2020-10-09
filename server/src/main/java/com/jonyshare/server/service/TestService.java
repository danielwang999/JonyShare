package com.jonyshare.server.service;


import com.jonyshare.server.domain.Test;
import com.jonyshare.server.domain.TestExample;
import com.jonyshare.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangQiang
 * @date 2020/10/8-9:18
 */
@Service
public class TestService {

    @Resource
    TestMapper testMapper;

    public List<Test> list() {
        TestExample testExample = new TestExample();
        testExample.setOrderByClause("id desc");
        return testMapper.selectByExample(testExample);
    }
}

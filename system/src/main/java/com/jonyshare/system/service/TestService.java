package com.jonyshare.system.service;

import com.jonyshare.system.domain.Test;
import com.jonyshare.system.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return testMapper.list();
    }
}

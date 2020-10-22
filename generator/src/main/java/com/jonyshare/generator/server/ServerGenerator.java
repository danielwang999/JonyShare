package com.jonyshare.generator.server;

import com.jonyshare.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author WangQiang
 * @date 2020/10/22-16:20
 */
public class ServerGenerator {

    public static void main(String[] args) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("test.ftl");
        FreemarkerUtil.generator("generator\\src\\main\\java\\com\\jonyshare\\generator\\test\\Test.java");
    }
}

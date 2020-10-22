package com.jonyshare.generator.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author WangQiang
 * @date 2020/10/22-16:14
 */
public class FreemarkerUtil {
    // 所有的模板文件都放在这里
    static String ftlPath = "generator\\src\\main\\java\\com\\jonyshare\\generator\\ftl\\";

    static Template temp;

    public static void initConfig(String ftlName) throws IOException {
        // 读取模板
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        temp = cfg.getTemplate(ftlName);
    }

    public static void generator(String fileName, Map<String, Object> map) throws IOException, TemplateException {
        // 根据模板生成文件
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        temp.process(map, bw);
        bw.flush();
        fw.close();
    }
}

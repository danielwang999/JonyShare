package com.jonyshare.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author WangQiang
 * @date 2020/11/26-15:24
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Value("${file.path}")
    private String FILE_PATH;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // localhost:9003/file/f/teacher/cpfJ8lLC-aaa.png
        registry.addResourceHandler("/f/**").addResourceLocations("file:" + FILE_PATH);
    }
}

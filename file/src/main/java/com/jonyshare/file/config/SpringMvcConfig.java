package com.jonyshare.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author WangQiang
 * @date 2020/11/26-15:24
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    // localhost:9003/file/f/cpfJ8lLC-aaa.png
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/f/**").addResourceLocations("file:F:/1OldE/JONYJAVA_UPLOAD_FILES/");
    }
}

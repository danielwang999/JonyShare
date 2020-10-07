package com.jonyshare.system;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SystemApplication {
    private static final Logger LOG = LoggerFactory.getLogger(SystemApplication.class);


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SystemApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("System地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}

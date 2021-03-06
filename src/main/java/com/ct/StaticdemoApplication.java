package com.ct;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ct.mapper")
@Slf4j
public class StaticdemoApplication {

    public static void main(String[] args) {
        log.info("---------------------------------------项目启动成功---------------------------------------");
        SpringApplication.run(StaticdemoApplication.class, args);

    }

}

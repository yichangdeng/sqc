package com.example.qc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 扫描mapper接口所在的包
@MapperScan("com/example/qc/dao")
public class QcApplication {

    public static void main(String[] args) {
        SpringApplication.run(QcApplication.class, args);
    }

}

package com.xzit;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan ("com.xzit.mapper")
@EnableScheduling
public class AutoRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoRentalApplication.class, args);
    }

//    oss-cn-chengdu.aliyuncs.com
//    https://auto-test-gule.oss-cn-chengdu.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20250512210144.jpg

}

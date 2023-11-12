package com.wt.hrentSystem;

import com.wt.hrentSystem.common.util.NginxUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@MapperScan("com.wt.hrentSystem.*.dao")
@SpringBootApplication
public class HrentApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrentApplication.class, args);
        //开启nginx
        //openNginx();
    }

    public static void openNginx(){
        //判断当前操作系统是不是windows
        if(NginxUtils.judgeOs()){
            //判断当前nginx是否开启
            if (NginxUtils.findProcess()) {
                return;
            }else{
                NginxUtils.start();
            }
        }
    }
}

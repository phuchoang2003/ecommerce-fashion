package org.example.ecommercefashion;

import com.longnh.annotions.EnableCommon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCommon
@EnableFeignClients
@EnableRetry
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
public class EcommerceFashionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceFashionApplication.class, args);
    }

}

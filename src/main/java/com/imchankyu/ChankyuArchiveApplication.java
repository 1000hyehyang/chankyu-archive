package com.imchankyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 메인 애플리케이션 클래스
 */
@SpringBootApplication
@EnableScheduling
public class ChankyuArchiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChankyuArchiveApplication.class, args);
    }
}

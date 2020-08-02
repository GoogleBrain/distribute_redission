package cn.hu.distributeredission.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class RedissionController {

    @RequestMapping("/redissionlock")
    public String redissionLock() {
        /**
         * 使用java API的方式实现分布式锁。
         */
        log.info("我进入了方法......");
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redisson = Redisson.create(config);
        RLock rLock = redisson.getLock("order");
        rLock.lock(30, TimeUnit.SECONDS);
        log.info("我活得了锁。。。。");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("我释放了锁。。。。");
            rLock.unlock();
        }
        return "完成";
    }
}

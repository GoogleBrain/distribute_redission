package cn.hu.distributeredission.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedissionController {

    @RequestMapping("/redissionlock")
    public String redissionLock() {
        System.out.println("我进入了方法......");
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redisson = Redisson.create(config);
        RLock rLock = redisson.getLock("order");
        rLock.lock(30, TimeUnit.SECONDS);
        System.out.println("我活得了锁。。。。");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("我释放了锁。。。。");
            rLock.unlock();
        }
        return "完成";
    }
}

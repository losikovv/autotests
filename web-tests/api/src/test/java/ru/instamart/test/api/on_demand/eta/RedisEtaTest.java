package ru.instamart.test.api.on_demand.eta;

import org.testng.annotations.Test;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;

public final class RedisEtaTest {

    @Test(groups = "debug")
    public void bar() {
        System.out.println("Keys count bar: " + RedisService.get(RedisManager.getConnection(Redis.ETA),"retailer_1"));
        RedisService.set(RedisManager.getConnection(Redis.ETA), "retailer_2", "data2", 100);
        System.out.println("Keys retailer_1 bar: " + RedisService.get(RedisManager.getConnection(Redis.ETA),"retailer_1"));
        System.out.println("Keys retailer_2 bar: " + RedisService.get(RedisManager.getConnection(Redis.ETA),"retailer_2"));
        System.out.println("Keys retailer_2 exist bar: " + RedisService.isExist(RedisManager.getConnection(Redis.ETA),"retailer_2"));
        RedisService.del(RedisManager.getConnection(Redis.ETA),"retailer_2");
        System.out.println("Keys retailer_2 exist bar: " + RedisService.isExist(RedisManager.getConnection(Redis.ETA),"retailer_2"));
        System.out.println("Keys retailer_1 bar: " + RedisService.get(RedisManager.getConnection(Redis.ETA),"store_*"));
        RedisService.getAll(RedisManager.getConnection(Redis.ETA)).forEach(System.out::println);
    }
}

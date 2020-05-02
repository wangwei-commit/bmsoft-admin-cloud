package com.bmsoft.cloud;

import com.bmsoft.cloud.authority.entity.auth.User;
import com.bmsoft.cloud.authority.enumeration.auth.Sex;
import com.bmsoft.cloud.cache.repository.CacheRepository;
import com.bmsoft.cloud.common.constant.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.function.Function;


@Component
@Slf4j
class CacTest {
    @Cacheable(value = CacheKey.REGISTER_USER, key = "#name")
    public User getMenu(String name) {
        log.info("name={}", name);
        return User.builder()
                .account(name).name("张三李四!@#$%^&*()_123")
                .id(6079967614237410571L)
                .sex(Sex.M)
                .createTime(LocalDateTime.now())
                .build();
    }

    @Cacheable(value = "bmsoft", keyGenerator = "keyGenerator")
    public User bmsoft() {
        return User.builder()
                .account("bmsoft").name("张三李四!@#$%^&*()_123")
                .id(6079967614237410571L)
                .sex(Sex.M)
                .createTime(LocalDateTime.now())
                .build();
    }

    @Cacheable(value = "test", key = "1")
    public User test() {
        return User.builder()
                .account("bmsoft").name("张三李四!@#$%^&*()_123")
                .id(6079967614237410571L)
                .sex(Sex.M)
                .createTime(LocalDateTime.now())
                .build();
    }
}

/**
 * 缓存测试类
 *
 * @author bmsoft
 * @date 2019/08/07
 */
@ComponentScan({"com.bmsoft.cloud"})
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class CacheTest {
    @Autowired
    CacTest cacheTest;
    @Autowired
    CacheRepository cacheRepository;

    @Test
    public void testCacheable() throws Exception {

        log.info("user={}", cacheTest.getMenu("aaa"));
        log.info("user={}", cacheTest.getMenu("aaa"));
        log.info("user={}", cacheTest.getMenu("aaa"));
        log.info("user={}", cacheTest.getMenu("aaa"));
        log.info("user={}", cacheTest.getMenu("aaa"));
        log.info("user={}", cacheTest.getMenu("bbb"));
        log.info("user={}", cacheTest.getMenu("ccc"));
        log.info("user={}", cacheTest.getMenu("ddd"));
        log.info("user={}", cacheTest.getMenu("eee"));
        log.info("bmsoft={}", cacheTest.bmsoft());
        log.info("test={}", cacheTest.test());
        log.info("end");

        Thread.sleep(3000);

        log.info("user={}", cacheTest.getMenu("aaa"));

    }

    @Test
    public void testGetOrDef() {
        Function<String, String> function = (key) -> {
            log.info("延迟加载了几次");
            return "延迟" + key;
        };
        String zuih = cacheRepository.getOrDef("zuih", function);
        log.info("zuih={}", zuih);
        zuih = cacheRepository.getOrDef("zuih", function);
        log.info("zuih={}", zuih);
    }

    @Test
    public void testCache() {
        User user = User.builder()
                .account("bmsoft").name("张三李四!@#$%^&*()_123")
                .id(6079967614237410571L)
                .sex(Sex.M)
                .createTime(LocalDateTime.now())
                .build();


        cacheRepository.set("wz", "厉害hello ");
        cacheRepository.set("wz2", "厉害hello 22");
        cacheRepository.setExpire("bmsoft", user, 3);

        String wz = cacheRepository.get("wz");
        User user2 = cacheRepository.get("bmsoft");
        System.out.println(wz);
        System.out.println(user2);
        System.out.println(cacheRepository.del("wz2"));
        User user3 = cacheRepository.get("bmsoft");
        System.out.println(user3);
    }
}

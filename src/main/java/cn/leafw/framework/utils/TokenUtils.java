package cn.leafw.framework.utils;

import cn.leafw.framework.utils.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author CareyWYR
 * @description
 * @date 2018/7/16 10:34
 */
@Slf4j
public class TokenUtils {

    private static RedisTemplate<String,String> redisTemplate = SpringContext.getBean("redisTemplate");

    public static String generateToken(String userId) {
        UUID uuid = UUID.randomUUID();
        String token = userId + "_" +uuid.toString().replaceAll("-","");
        log.info("generate token: {}", token);
        redisTemplate.opsForValue().set(token,token,600L, TimeUnit.SECONDS);
        return token;
    }

    public static void refreshTokenCache(String token) {
        log.info("refresh token");
        redisTemplate.opsForValue().set(token,token,600L,TimeUnit.SECONDS);
    }

    public static void invalidToken(String token) {
        boolean deleteResult = redisTemplate.delete(token);
        if(deleteResult){
            log.info("token已删除");
        }else{
            log.error("token删除失败，失效或不存在！");
        }
    }

    public static boolean validateToken(String token) {
        String result = redisTemplate.opsForValue().get(token);
        if(null != result){
            return true;
        }
        return false;
    }
}

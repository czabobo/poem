package com.baizhi.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component
public class MyRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    public void saveRedis(String key, String val){

        //Double score = redisTemplate.opsForZSet().score(key, val);
        Double score = redisTemplate.opsForZSet().score(key, val);
        if(score==null){
            redisTemplate.opsForZSet().add(key,val,1);
        }else {
            redisTemplate.opsForZSet().incrementScore(key,val,1);
        }

    }

    public Map<String, Double> selectByScore(String key){
        Set<String> ss = redisTemplate.opsForZSet().reverseRangeByScore(key, 0, 100);
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        for (String s : ss) {
            Double score = redisTemplate.opsForZSet().score(key, s);
            map.put(s,score);
        }
        return map;
    }
}

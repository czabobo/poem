package com.baizhi;

import com.baizhi.dao.PoemDao;
import com.baizhi.entity.Category;
import com.baizhi.entity.Poem;
import com.baizhi.es.MyRepository;
import com.baizhi.redis.MyRedis;
import com.baizhi.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest(classes = PoemApplication.class)
@RunWith(SpringRunner.class)
public class PoemApplicationTests {
    @Autowired
    private PoemDao poemDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MyRepository myRepository;
    @Autowired
    MyRedis myRedis;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        List<Poem> poems = poemDao.selectByPage(0, 2);
        for (Poem poem : poems) {
            System.out.println("poem = " + poem);
        }
        Integer integer = poemDao.selectCount();
        System.out.println("integer = " + integer);
    }

    @Test
    public void testCategory() {
        List<Category> allCategory = categoryService.getAllCategory();
        for (Category category : allCategory) {
            System.out.println("category = " + category);
        }
    }

    @Test
    public void testAdd() {
        Poem poem = new Poem();
        poem.setId("1").setAuthor("aa").setAuthordes("aaa").setCategoryId("2cec1e5d-8b6a-40b9-a98d-bf2fe5846a2f").setContent("aaa").setName("aa").setOrigin("aa").setType("aaa");
        poemDao.add(poem);
    }

    @Test
    public void testUpdate() {
        Poem poem = new Poem();
        poem.setId("1").setAuthor("aa").setAuthordes("aaa").setCategoryId("2cec1e5d-8b6a-40b9-a98d-bf2fe5846a2f").setContent("bbbb").setName("aa").setOrigin("aa").setType("aaa");
        poemDao.update(poem);
    }

    @Test
    public void delete() {
        String[] ids = {"1"};
        poemDao.delete(ids);
    }

    @Test
    public void test1() {
        HashSet<String> set = new HashSet<>();
        List<Poem> poems = poemDao.selectAll();
        for (Poem poem : poems) {
            set.add(poem.getAuthor());
        }

        for (String s : set) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void test2() {
        List<Poem> poems = myRepository.search("李白", "2cec1e5d-8b6a-40b9-a98d-bf2fe5846a2f", null);
        for (Poem poem : poems) {
            System.out.println("poem = " + poem);
        }
    }

    @Test
    public void testredis() {
        /*myRedis.saveRedis("ss","杜甫");
        myRedis.saveRedis("ss","杜甫");
        myRedis.saveRedis("ss","杜甫");
        myRedis.saveRedis("ss","李白");
        myRedis.saveRedis("ss","李白");
        myRedis.saveRedis("ss","唐诗");*/



        Map<String, Double> map = myRedis.selectByScore("ss");
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println("entry = " + entry.getKey());
            System.out.println("entry = " + entry.getValue());
            System.out.println("===============================");
        }

    }

    @Test
    public void testDel() {
        Set<String> ss = redisTemplate.opsForZSet().reverseRangeByScore("ss", 0, 100);
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        for (String s : ss) {
            Double score = redisTemplate.opsForZSet().score("ss", s);
            map.put(s,score);
        }
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println(entry.getKey()+"====>"+entry.getValue());
        }
    }
}

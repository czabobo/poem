package com.baizhi.controller;

import com.baizhi.entity.Poem;
import com.baizhi.es.EsService;
import com.baizhi.es.MyRepository;
import com.baizhi.redis.MyRedis;
import com.baizhi.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("poem")
public class PoemController {
    @Autowired
    private PoemService poemService;
    @Autowired
    private EsService esService;
    @Autowired
    private MyRepository myRepository;
    @Autowired
    private MyRedis myRedis;

    //后台：分页查询
    @RequestMapping("getAllPoem")
    public Map<String, Object> getAllPoem(Integer page, Integer rows) {
        return poemService.getAllPoem(page, rows);
    }

    //后台:增删改
    @RequestMapping("edit")
    public void edit(String oper, Poem poem, String[] id) {
        if (oper.equals("add")) {
            poemService.insert(poem);
        } else if (oper.equals("edit")) {
            poemService.update(poem);
        } else if (oper.equals("del")) {
            poemService.delete(id);
        }
    }

    //后台：添加索引文档
    @RequestMapping("addEs")
    public void addEs() {
        esService.addEs();
    }

    //后台：删除索引中文档
    @RequestMapping("deleteEs")
    public void deleteEs(){
        esService.deleteEs();
    }

    //前台：查所有作者展示
    @RequestMapping("getAll")
    public Set<String> getAll(){
        Set<String> set = poemService.selectAll();
        return set;
    }

    //前台：查前12个，--->要改成查所有分页吧...
    @RequestMapping("selectAll")
    public List<Poem> selectAll(){
        return poemService.selectByRownum();
    }

    //前台:搜索
    @RequestMapping("searchEs")
    public List<Poem> searchEs(String value,String categoryId,String author){
        List<Poem> poems = myRepository.search(value, categoryId, author);
        return poems;
    }

    //后台:查询热搜
    @RequestMapping("hot")
    public Map<String,Double> hot(){
        Map<String, Double> hot = myRedis.selectByScore("hot");
        return hot;
    }

}

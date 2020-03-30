package com.baizhi.service;

import com.baizhi.dao.PoemDao;
import com.baizhi.entity.Poem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PoemServiceImpl implements PoemService {
    @Autowired
    private PoemDao poemDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> getAllPoem(Integer page, Integer rows) {
        //参数：page：当前页  rows：每页展示几条
        /* 返回数据
         * 总页数 total
         * 总条数 records
         * 当前页 page
         * 数据集合: rows
         * */
        Integer start = (page-1)*rows;
        List<Poem> poems = poemDao.selectByPage(start, rows);
        Integer records = poemDao.selectCount();
        Integer total = records%rows==0?records/rows:records/rows+1;

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("records",records);
        map.put("page",page);
        map.put("rows",poems);

        return map;
    }

    @Override
    public void insert(Poem poem) {
        poem.setId(UUID.randomUUID().toString());
        poemDao.add(poem);
    }

    @Override
    public void update(Poem poem) {
        poemDao.update(poem);
    }

    @Override
    public void delete(String[] ids) {
        poemDao.delete(ids);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Set<String> selectAll() {
        HashSet<String> set = new HashSet<>();
        List<Poem> poems = poemDao.selectAll();
        for (Poem poem : poems) {
            set.add(poem.getAuthor());
        }
        return set;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Poem> selectByRownum() {
        List<Poem> poems = poemDao.selectByRownum();
        return poems;
    }
}

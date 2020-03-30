package com.baizhi.service;

import com.baizhi.entity.Poem;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PoemService {
    public Map<String,Object> getAllPoem(Integer page,Integer rows);
    public void insert(Poem poem);
    public void update(Poem poem);
    public void delete(String[] ids);
    public Set<String> selectAll();
    public List<Poem> selectByRownum();
}

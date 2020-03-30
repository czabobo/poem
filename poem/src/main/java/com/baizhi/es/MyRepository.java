package com.baizhi.es;

import com.baizhi.entity.Poem;

import java.util.List;

public interface MyRepository {

    public List<Poem> search(String value,String categoryId,String author);
}

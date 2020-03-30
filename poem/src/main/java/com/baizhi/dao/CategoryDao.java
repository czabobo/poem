package com.baizhi.dao;

import com.baizhi.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface CategoryDao {
    @Select("select * from t_category")
    public List<Category> selectAll();
}

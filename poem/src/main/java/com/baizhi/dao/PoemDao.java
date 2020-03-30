package com.baizhi.dao;

import com.baizhi.entity.Poem;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface PoemDao {
    //分页查询
    @Select("select * from t_poem LIMIT #{start},#{rows}")
    public List<Poem> selectByPage(@Param("start") Integer start,@Param("rows") Integer rows);
    @Select("SELECT COUNT(id) from t_poem")
    public Integer selectCount();
    @Insert("insert into t_poem(id,name,author,type,content,authordes,origin,categoryId) values(#{id}, #{name}, #{author}, #{type}, #{content},#{authordes},#{origin},#{categoryId})")
    public void add(Poem poem);
    @Update("update t_poem set name=#{name},author=#{author},type=#{type},content=#{content},authordes=#{authordes},origin=#{origin},categoryId=#{categoryId} where id=#{id}")
    public void update(Poem poem);
    public void delete(String[] id);
    @Select("select * from t_poem")
    public List<Poem> selectAll();
    @Select("select * from t_poem limit 0,16")
    public List<Poem> selectByRownum();


}

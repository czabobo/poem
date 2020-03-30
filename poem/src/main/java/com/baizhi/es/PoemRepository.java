package com.baizhi.es;


import com.baizhi.entity.Poem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface PoemRepository extends ElasticsearchRepository<Poem,String> {

}

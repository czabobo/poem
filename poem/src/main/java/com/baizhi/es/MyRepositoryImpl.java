package com.baizhi.es;

import com.baizhi.entity.Poem;
import com.baizhi.redis.MyRedis;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Configuration
public class MyRepositoryImpl implements MyRepository {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private MyRedis myRedis;

    @Override
    public List<Poem> search(String value, String categoryId, String author) {
        //热搜存储redis
        myRedis.saveRedis("hot",value);


        System.out.println("value = " + value);
        System.out.println("categoryId = " + categoryId);
        System.out.println("author = " + author);

        HighlightBuilder.Field highlight = new HighlightBuilder
                .Field("*")
                .preTags("<span style='color:red'>")
                .postTags("</span>")
                .requireFieldMatch(false);

        SearchQuery query = null;
        if (categoryId.equals("") && author.equals("")) {
            //if(categoryId==null&&author==null) {
            query = new NativeSearchQueryBuilder()
                    .withIndices("poem")
                    .withTypes("poem")
                    .withQuery(multiMatchQuery(value, "name", "author", "content", "type", "authordes", "origin"))
                    .withHighlightFields(highlight)
                    .build();
        } else if (!(categoryId.equals("")) && !author.equals("")) {
            //}else if(categoryId!=null&author!=null) {
            query = new NativeSearchQueryBuilder()
                    .withIndices("poem")
                    .withTypes("poem")
                    .withFilter(boolQuery().must(multiMatchQuery(value, "name", "author", "content", "type", "authordes", "origin")).filter(termQuery("categoryId", categoryId)).filter(termQuery("author", author)))
                    .withHighlightFields(highlight)
                    .build();
        } else if (!(categoryId.equals("")) && author.equals("")) {
            //}else if (categoryId!=null&author==null){
            query = new NativeSearchQueryBuilder()
                    .withIndices("poem")
                    .withTypes("poem")
                    .withFilter(boolQuery().must(multiMatchQuery(value, "name", "author", "content", "type", "authordes", "origin")).filter(termQuery("categoryId", categoryId)))
                    .withHighlightFields(highlight)
                    .build();
        } else {
            query = new NativeSearchQueryBuilder()
                    .withIndices("poem")
                    .withTypes("poem")
                    .withFilter(boolQuery().must(multiMatchQuery(value, "name", "author", "content", "type", "authordes", "origin")).filter(termQuery("author", author)))
                    .withHighlightFields(highlight)
                    .build();
        }


        AggregatedPage<Poem> poems = elasticsearchTemplate.queryForPage(query, Poem.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                SearchHit[] hits = response.getHits().getHits();
                ArrayList<Poem> poems = new ArrayList<>();
                for (SearchHit hit : hits) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    Poem poem = new Poem();
                    poem.setId(map.get("id").toString())
                            .setAuthor(map.get("author").toString())
                            .setType(map.get("type").toString())
                            .setOrigin(map.get("origin").toString())
                            .setName(map.get("name").toString())
                            .setContent(map.get("content").toString())
                            .setCategoryId(map.get("categoryId").toString())
                            .setAuthordes(map.get("authordes").toString())
                            .setHref(map.get("href").toString())
                            .setImagepath(map.get("imagepath").toString());

                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (highlightFields.get("name") != null) {
                        poem.setName(highlightFields.get("name").getFragments()[0].toString());
                    }
                    if (highlightFields.get("author") != null) {
                        poem.setAuthor(highlightFields.get("author").getFragments()[0].toString());
                    }
                    if (highlightFields.get("type") != null) {
                        poem.setType(highlightFields.get("type").getFragments()[0].toString());
                    }
                    if (highlightFields.get("content") != null) {
                        poem.setContent(highlightFields.get("content").getFragments()[0].toString());
                    }
                    if (highlightFields.get("authordes") != null) {
                        poem.setAuthordes(highlightFields.get("authordes").getFragments()[0].toString());
                    }
                    poems.add(poem);
                }
                AggregatedPageImpl<T> ts = new AggregatedPageImpl<>((List<T>) poems);
                return ts;
            }
        });

        return poems.getContent();
    }

}



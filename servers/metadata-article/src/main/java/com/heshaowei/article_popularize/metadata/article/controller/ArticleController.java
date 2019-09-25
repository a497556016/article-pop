package com.heshaowei.article_popularize.metadata.article.controller;

import com.heshaowei.article_popularize.common.bean.response.Result;
import com.heshaowei.article_popularize.entity.Article;
import com.heshaowei.article_popularize.entity.User;
import com.heshaowei.article_popularize.metadata.article.repository.ArticleRepository;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Article article){
        article.setDate(new Date());
        /*User user = new User();
        user.setId(new ObjectId());
        user.setUsername("admin");
        user.setWxNickname("哈哈");
        user.setWxId("123456789");
        user.setWxUsername("hehe");
        article.setAuthor(user);*/
        this.articleRepository.save(article);
        return ResponseEntity.ok(article);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Article>> list(int size, int page, String tag){
        Page<Article> pageResponse = null;

        Sort sort = Sort.by(Sort.Order.desc("date"));
        Pageable pageable = PageRequest.of(page, size, sort);
        if("news_hot".equals(tag)){

            pageResponse = this.articleRepository.findAll(pageable);
        }else if("news_recommend".equals(tag)){
            //随机查
            TypedAggregation<Article> agg = Aggregation.newAggregation(Article.class, Aggregation.sample(size));
            System.out.println(agg.toString());
            AggregationResults<Article> results = this.mongoTemplate.aggregate(agg, Article.class);

            List<Article> list = results.getMappedResults();
            pageResponse = new PageImpl<>(list, pageable, ((page+1)*size)+1);

//            this.articleRepository.findWithRandom(size);
        }else {
            Article probe = new Article();
            probe.setTag(tag);

            pageResponse = this.articleRepository.findAll(Example.of(probe), pageable);
        }

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> get(@PathVariable("id") String id){
        Article article = this.articleRepository.findById(new ObjectId(id)).get();
        return ResponseEntity.ok(article);
    }
}

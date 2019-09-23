package com.heshaowei.article_popularize.metadata.article.controller;

import com.heshaowei.article_popularize.common.bean.response.Result;
import com.heshaowei.article_popularize.entity.Article;
import com.heshaowei.article_popularize.entity.User;
import com.heshaowei.article_popularize.metadata.article.repository.ArticleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<Article>> list(int size, int page){
        Sort sort = Sort.by(Sort.Order.desc("date"));
        Page<Article> page1 = this.articleRepository.findAll(PageRequest.of(page, size, sort));
        return ResponseEntity.ok(page1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> get(@PathVariable("id") String id){
        Article article = this.articleRepository.findById(new ObjectId(id)).get();
        return ResponseEntity.ok(article);
    }
}

package com.heshaowei.article_popularize.metadata.article.controller;

import com.heshaowei.article_popularize.entity.Article;
import com.heshaowei.article_popularize.entity.Comment;
import com.heshaowei.article_popularize.metadata.article.repository.ArticleRepository;
import com.heshaowei.article_popularize.metadata.article.repository.CommentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping
    public ResponseEntity<Comment> add(@RequestBody Comment comment){
        Article article = this.articleRepository.findById(comment.getArticleId()).orElse(new Article());
        article.setCommentsCount(article.getCommentsCount()+1);
        this.articleRepository.save(article);

        comment.setDate(new Date());
        this.commentRepository.save(comment);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Page> queryByArticle(@PathVariable("articleId") String articleId, int page, int size){
        Comment probe = new Comment();
        probe.setArticleId(new ObjectId(articleId));
        Page<Comment> pageResult = this.commentRepository.findAll(Example.of(probe), PageRequest.of(page, size, Sort.by(Sort.Order.desc("date"))));

        return ResponseEntity.ok(pageResult);
    }
}

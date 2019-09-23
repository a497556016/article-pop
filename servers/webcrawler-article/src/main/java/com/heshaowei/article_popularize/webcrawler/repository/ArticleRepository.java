package com.heshaowei.article_popularize.webcrawler.repository;

import com.heshaowei.article_popularize.entity.Article;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, ObjectId> {
}

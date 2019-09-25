package com.heshaowei.article_popularize.metadata.article.repository;

import com.heshaowei.article_popularize.entity.Article;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<Article, ObjectId> {

    @Query("{ \"aggregate\" : \"__collection__\", \"pipeline\" : [{ \"$sample\" : { \"size\" : ?0 } }] }")
    List<Article> findWithRandom(int size);
}

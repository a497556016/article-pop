package com.heshaowei.article_popularize.metadata.article.repository;

import com.heshaowei.article_popularize.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
}

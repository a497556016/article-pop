package com.heshaowei.article_popularize.metadata.article.repository;

import com.heshaowei.article_popularize.entity.UserArticleManage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserArticleManageRepository extends MongoRepository<UserArticleManage, ObjectId> {

    UserArticleManage findByArticleIdAndUserId(ObjectId articleId, ObjectId userId);
}

package com.heshaowei.article_popularize.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class UserArticleManage {
    private ObjectId id;
    private ObjectId userId;
    private String username;
    private ObjectId articleId;
    private String articleTitle;
    /**
     * 被阅读次数
     */
    private Long viewsCount;
    /**
     * 打开名片次数
     */
    private Long userCardCount;
}

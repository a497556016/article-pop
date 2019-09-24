package com.heshaowei.article_popularize.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Comment {
    private ObjectId id;
    private ObjectId articleId;
    private List<SubComment> subComments;

    private String username;
    private String content;

    @Data
    private class SubComment {
        private String replayUsername;

        private String username;
        private String content;
    }
}

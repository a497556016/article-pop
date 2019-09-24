package com.heshaowei.article_popularize.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Document
@Data
public class Comment {
    private ObjectId id;
    @NotBlank(message = "图文ID不能为空！")
    private ObjectId articleId;
    private List<SubComment> subComments;

    private String username;
    private String content;
    private Date date;

    @Data
    private class SubComment {
        private String replayUsername;

        private String username;
        private String content;
        private Date date;
    }
}

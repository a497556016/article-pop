package com.heshaowei.article_popularize.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Document
public class Article {
    private ObjectId id;
    private String itemId;
    @NotEmpty(message = "文章标题不能为空！")
    private String title;
    private String cover;
    private String abstractText;
    @NotBlank(message = "文章内容不能为空！")
    private String content;
    private String source;
    private String sourceUrl;
    private Author author;
    private Date date;
    private String tag;
    private String chineseTag;
    private Long commentsCount;
    private Long viewsCount;
    private Long likesCount;
    private Long collectsCount;
    private Date lastModified;

    @Data
    private class Author{
        private String id;
        private String wxNickname;
        private String wxAvatar;
    }

    public void setAuthor(User user){
        Author author = new Author();
        author.id = user.getId().toHexString();
        author.wxNickname = user.getWxNickname();
        author.wxAvatar = user.getWxAvatar();
        this.author = author;
    }
}

package com.heshaowei.article_popularize.entity;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

/**
* 文章简略信息
*/
@Data
public class SimpleArticle{
    private ObjectId articleId;
    private String title;
    private String abstractText;
    /**
     * 添加日期
     */
    private Date date;

    public static SimpleArticle createByArticle(Article article){
        SimpleArticle simpleArticle = new SimpleArticle();
        simpleArticle.setArticleId(article.getId());
        simpleArticle.setTitle(article.getTitle());
        simpleArticle.setAbstractText(article.getAbstractText());
        simpleArticle.setDate(new Date());
        return simpleArticle;
    }
}
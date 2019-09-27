package com.heshaowei.article_popularize.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class User {
    private ObjectId id;
    private String username;
    private String password;
    private String avatar;
    /**
     * 公司名称
     */
    private String company;
    /**
     * 广告宣言
     */
    private String declaration;
    /**
     * 职位
     */
    private String post;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 微信ID
     */
    private String wxId;
    /**
     * 微信二维码图片
     */
    private String wxQRCode;
    /**
     * 微信头像
     */
    private String wxAvatar;
    /**
     * 微信昵称
     */
    private String wxNickname;
    /**
     * 微信账号
     */
    private String wxUsername;

    /**
     * 收藏文章
     */
    private List<SimpleArticle> collectedArticles;

    /**
     * 点赞文章
     */
    private List<SimpleArticle> likedArticles;


}

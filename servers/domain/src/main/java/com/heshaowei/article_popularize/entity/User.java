package com.heshaowei.article_popularize.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {
    private ObjectId id;
    private String username;
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
}
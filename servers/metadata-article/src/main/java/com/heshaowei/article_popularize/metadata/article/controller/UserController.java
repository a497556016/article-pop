package com.heshaowei.article_popularize.metadata.article.controller;

import com.heshaowei.article_popularize.common.exception.ErrorMessageException;
import com.heshaowei.article_popularize.entity.SimpleArticle;
import com.heshaowei.article_popularize.entity.User;
import com.heshaowei.article_popularize.metadata.article.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        if(null != user.getId()){
            User update = this.userRepository.findById(user.getId()).orElse(null);
            if(null != update) {
                if(StringUtils.isNotBlank(user.getAvatar())) {
                    update.setAvatar(user.getAvatar());
                }
                if(StringUtils.isNotBlank(user.getWxQRCode())){
                    update.setWxQRCode(user.getWxQRCode());
                }
                if(StringUtils.isNotBlank(user.getPassword())) {
                    update.setPassword(user.getPassword());
                }
                if(StringUtils.isNotBlank(user.getUsername())){
                    update.setUsername(user.getUsername());
                }
                if(StringUtils.isNotBlank(user.getCompany())){
                    update.setCompany(user.getCompany());
                }
                if(StringUtils.isNotBlank(user.getPost())){
                    update.setPost(user.getPost());
                }
                if(StringUtils.isNotBlank(user.getPhone())){
                    update.setPhone(user.getPhone());
                }
                if(StringUtils.isNotBlank(user.getEmail())){
                    update.setEmail(user.getEmail());
                }
                if(StringUtils.isNotBlank(user.getDeclaration())){
                    update.setDeclaration(user.getDeclaration());
                }
                user = this.userRepository.save(update);
            }
        }else {
            user = this.userRepository.save(user);
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/loginWithPwd")
    public ResponseEntity loginWithPwd(@NotBlank(message = "用户名不能为空！") String username, @NotBlank(message = "密码不能为空！") String password){
        User probe = new User();
        probe.setUsername(username);
        probe.setPassword(password);
        User user = this.userRepository.findOne(Example.of(probe)).orElse(null);
        if(null != user){
            return ResponseEntity.ok(user);
        }else{
            throw new ErrorMessageException("账号或密码错误，请重试！");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") String id){
        User user = this.userRepository.findById(new ObjectId(id)).orElse(new User());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/deleteLikedArticle/{userId}/{articleId}")
    public ResponseEntity deleteLikedArticle(@NotBlank @PathVariable("userId") String userId, @NotBlank @PathVariable("articleId") String articleId){
        User user = this.userRepository.findById(new ObjectId(userId)).orElse(null);
        if(null == user) {
            throw new ErrorMessageException("用户不存在！");
        }
        List<SimpleArticle> articles = user.getLikedArticles().stream().filter(simpleArticle -> articleId.equals(simpleArticle.getArticleId().toHexString())).collect(Collectors.toList());
        if(null != articles) {
            user.getLikedArticles().removeAll(articles);
        }
        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/deleteCollectedArticle/{userId}/{articleId}")
    public ResponseEntity deleteCollectedArticle(@NotBlank @PathVariable("userId") String userId, @NotBlank @PathVariable("articleId") String articleId){
        User user = this.userRepository.findById(new ObjectId(userId)).orElse(null);
        if(null == user) {
            throw new ErrorMessageException("用户不存在！");
        }
        List<SimpleArticle> articles = user.getCollectedArticles().stream().filter(simpleArticle -> articleId.equals(simpleArticle.getArticleId().toHexString())).collect(Collectors.toList());
        if(null != articles) {
            user.getCollectedArticles().removeAll(articles);
        }
        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}

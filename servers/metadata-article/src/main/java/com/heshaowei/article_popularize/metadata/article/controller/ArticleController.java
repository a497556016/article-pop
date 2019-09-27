package com.heshaowei.article_popularize.metadata.article.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heshaowei.article_popularize.common.bean.response.Result;
import com.heshaowei.article_popularize.common.exception.ErrorMessageException;
import com.heshaowei.article_popularize.entity.Article;
import com.heshaowei.article_popularize.entity.SimpleArticle;
import com.heshaowei.article_popularize.entity.User;
import com.heshaowei.article_popularize.entity.UserArticleManage;
import com.heshaowei.article_popularize.metadata.article.repository.ArticleRepository;
import com.heshaowei.article_popularize.metadata.article.repository.UserArticleManageRepository;
import com.heshaowei.article_popularize.metadata.article.repository.UserRepository;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserArticleManageRepository userArticleManageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Article article){
        article.setDate(new Date());
        /*User user = new User();
        user.setId(new ObjectId());
        user.setUsername("admin");
        user.setWxNickname("哈哈");
        user.setWxId("123456789");
        user.setWxUsername("hehe");
        article.setAuthor(user);*/
        this.articleRepository.save(article);
        return ResponseEntity.ok(article);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Article>> list(int size, int page, String tag){
        Page<Article> pageResponse = null;

        Sort sort = Sort.by(Sort.Order.desc("date"));
        Pageable pageable = PageRequest.of(page, size, sort);
        if("news_hot".equals(tag)){

            pageResponse = this.articleRepository.findAll(pageable);
        }else if("news_recommend".equals(tag)){
            //随机查
            TypedAggregation<Article> agg = Aggregation.newAggregation(Article.class, Aggregation.sample(size));
            System.out.println(agg.toString());
            AggregationResults<Article> results = this.mongoTemplate.aggregate(agg, Article.class);

            List<Article> list = results.getMappedResults();
            pageResponse = new PageImpl<>(list, pageable, ((page+1)*size)+1);

//            this.articleRepository.findWithRandom(size);
        }else {
            Article probe = new Article();
            probe.setTag(tag);

            pageResponse = this.articleRepository.findAll(Example.of(probe), pageable);
        }

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> get(@PathVariable("id") String id){
        Article article = this.articleRepository.findById(new ObjectId(id)).get();

        return ResponseEntity.ok(article);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Map> getDetail(@PathVariable("id") String id, HttpServletRequest request){
        Map<String, Object> result = Maps.newHashMap();

        Article article = this.articleRepository.findById(new ObjectId(id)).get();
        result.put("article", article);
        String userId = request.getHeader("userId");
        if(null != userId){
            User user = this.userRepository.findById(new ObjectId(userId)).orElse(null);
            if(null != user) {
                List<SimpleArticle> likes = user.getLikedArticles();
                if(null != likes){
                    boolean liked = likes.stream().anyMatch(a -> id.equals(a.getArticleId().toHexString()));
                    result.put("liked", liked);
                }
                List<SimpleArticle> collects = user.getCollectedArticles();
                if(null != collects){
                    boolean collected = collects.stream().anyMatch(a -> id.equals(a.getArticleId().toHexString()));
                    result.put("collected", collected);
                }
            }
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/view")
    public ResponseEntity update(@NotBlank String articleId, String userId, String wxUsername){
        Article article = this.articleRepository.findById(new ObjectId(articleId)).orElse(null);
        if(null == article){
            throw new ErrorMessageException("articleId 错误");
        }
        article.setViewsCount(article.getViewsCount()==null?1:(article.getViewsCount()+1));
        this.articleRepository.save(article);


        if(null != userId) {
            UserArticleManage userArticleManage = this.userArticleManageRepository.findByArticleIdAndUserId(new ObjectId(articleId), new ObjectId(userId));
            if(null == userArticleManage) {
                userArticleManage = new UserArticleManage();
                userArticleManage.setArticleId(article.getId());
                userArticleManage.setArticleTitle(article.getTitle());
                User user = this.userRepository.findById(new ObjectId(userId)).orElse(new User());
                userArticleManage.setUserId(user.getId());
                userArticleManage.setUsername(user.getUsername());
            }
            userArticleManage.setViewsCount(userArticleManage.getViewsCount()==null?1:(userArticleManage.getViewsCount()+1));
            this.userArticleManageRepository.save(userArticleManage);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/checkUserCard")
    public ResponseEntity checkUserCard(@NotBlank String articleId, @NotBlank String userId){
        UserArticleManage userArticleManage = this.userArticleManageRepository.findByArticleIdAndUserId(new ObjectId(articleId), new ObjectId(userId));
        if(null == userArticleManage){
            userArticleManage = new UserArticleManage();
            Article article = this.articleRepository.findById(new ObjectId(articleId)).orElse(null);
            if(null == article){
                throw new ErrorMessageException("articleId 错误");
            }
            userArticleManage.setArticleId(article.getId());
            userArticleManage.setArticleTitle(article.getTitle());
            User user = this.userRepository.findById(new ObjectId(userId)).orElse(new User());
            userArticleManage.setUserId(user.getId());
            userArticleManage.setUsername(user.getUsername());
        }
        userArticleManage.setUserCardCount(userArticleManage.getUserCardCount()==null?1:(userArticleManage.getUserCardCount() + 1));
        this.userArticleManageRepository.save(userArticleManage);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/like")
    public ResponseEntity<String> like(@NotBlank String articleId, @NotBlank String userId, @NotNull Boolean liked){
        User user = this.userRepository.findById(new ObjectId(userId)).orElse(null);
        if(null == user) {
            throw new ErrorMessageException("userId 错误");
        }
        Article article = this.articleRepository.findById(new ObjectId(articleId)).orElse(null);
        if(null == article){
            throw new ErrorMessageException("articleId 错误");
        }
        List<SimpleArticle> articles = user.getLikedArticles();
        if(null == articles){
            articles = Lists.newArrayList();
            user.setLikedArticles(articles);
        }
        SimpleArticle simpleArticle = articles.stream().filter(sa -> sa.getArticleId().equals(article.getId())).findFirst().orElse(null);

        if(null == simpleArticle && liked) {
            simpleArticle = SimpleArticle.createByArticle(article);
            articles.add(simpleArticle);

            article.setLikesCount(article.getLikesCount()==null?1:(article.getLikesCount()+1));
        }else if(null != simpleArticle && !liked){
            articles.remove(simpleArticle);

            article.setLikesCount(article.getLikesCount()==null?0:(article.getLikesCount()-1));
        }
        this.userRepository.save(user);


        this.articleRepository.save(article);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/collect")
    public ResponseEntity<String> collect(@NotBlank String articleId, @NotBlank String userId, @NotNull Boolean collected){
        User user = this.userRepository.findById(new ObjectId(userId)).orElse(null);
        if(null == user) {
            throw new ErrorMessageException("userId 错误");
        }
        Article article = this.articleRepository.findById(new ObjectId(articleId)).orElse(null);
        if(null == article){
            throw new ErrorMessageException("articleId 错误");
        }
        List<SimpleArticle> articles = user.getCollectedArticles();
        if(null == articles){
            articles = Lists.newArrayList();
            user.setCollectedArticles(articles);
        }
        SimpleArticle simpleArticle = articles.stream().filter(sa -> sa.getArticleId().equals(article.getId())).findFirst().orElse(null);

        if(null == simpleArticle && collected) {
            simpleArticle = SimpleArticle.createByArticle(article);
            articles.add(simpleArticle);

            article.setCollectsCount(article.getCollectsCount()==null?1:(article.getCollectsCount()+1));
        }else if(null != simpleArticle && !collected){
            articles.remove(simpleArticle);

            article.setCollectsCount(article.getCollectsCount()==null?0:(article.getCollectsCount()-1));
        }
        this.userRepository.save(user);

        this.articleRepository.save(article);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/view")
    public void redirect(HttpServletRequest request, HttpServletResponse response, String host, String path){
        try {
            response.sendRedirect(URLDecoder.decode(host, "utf-8")+"#"+URLDecoder.decode(path, "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

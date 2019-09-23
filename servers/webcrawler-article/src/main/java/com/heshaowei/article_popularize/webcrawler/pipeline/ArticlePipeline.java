package com.heshaowei.article_popularize.webcrawler.pipeline;

import com.heshaowei.article_popularize.entity.Article;
import com.heshaowei.article_popularize.webcrawler.repository.ArticleRepository;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ArticlePipeline implements Pipeline {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Object _articleInfos = resultItems.get("articleInfos");
        Object _articleInfo = resultItems.get("articleInfo");
        if(null != _articleInfos) {
            List<Article> articleInfos = (List<Article>)_articleInfos;
            articleInfos.forEach(article -> {
                Article probe = new Article();
                probe.setItemId(article.getItemId());
                long count = this.articleRepository.count(Example.of(probe));
                if(count == 0){
                    if(null == article.getDate()) {
                        article.setDate(new Date());
                    }
                    this.articleRepository.save(article);
                }
            });
        }else if(null != _articleInfo){
            Map<String, Object> articleInfo = (Map<String, Object>) _articleInfo;
            Article probe = new Article();
            probe.setItemId(articleInfo.get("itemId").toString());
            Article update = this.articleRepository.findOne(Example.of(probe)).orElse(null);
            if(null != update) {
                String content = StringEscapeUtils.unescapeHtml(articleInfo.get("content").toString());
                update.setContent(content);
                this.articleRepository.save(update);
            }
//            System.out.println("文章详细信息：\t"+update);
        }
    }
}

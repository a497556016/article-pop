package com.heshaowei.article_popularize.webcrawler.task;

import com.heshaowei.article_popularize.webcrawler.pageprocessor.ToutiaoPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ToutiaoCrawlerTask {

    @Autowired
    private ToutiaoPageProcessor toutiaoPageProcessor;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void execute(){
        this.toutiaoPageProcessor.run();
    }
}

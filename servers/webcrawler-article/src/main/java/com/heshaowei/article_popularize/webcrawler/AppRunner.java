package com.heshaowei.article_popularize.webcrawler;

import com.heshaowei.article_popularize.webcrawler.pageprocessor.ToutiaoPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private ToutiaoPageProcessor toutiaoPageProcessor;

    @Override
    public void run(String... args) throws Exception {
        this.toutiaoPageProcessor.run();
    }
}

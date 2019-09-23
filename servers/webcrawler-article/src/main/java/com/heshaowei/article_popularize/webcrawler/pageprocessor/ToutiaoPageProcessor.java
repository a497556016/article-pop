package com.heshaowei.article_popularize.webcrawler.pageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.heshaowei.article_popularize.entity.Article;
import com.heshaowei.article_popularize.webcrawler.pipeline.ArticlePipeline;
import com.heshaowei.article_popularize.webcrawler.util.JavaScriptUtil;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ToutiaoPageProcessor implements PageProcessor {

    @Autowired
    private ArticlePipeline articlePipeline;

    private static String url = "https://www.toutiao.com/api/pc/feed/?category=news_hot&utm_source=toutiao&widen=1" +
            "&max_behot_time=0" +
            "&max_behot_time_tmp=0" +
            "&tadrequire=true" +
            "&as=A1B51D58921EA15" +
            "&cp=5D829E3A71F5AE1" +
            "&_signature=OIQVVAAAZQwxyG9BtAmvUziEFU";

    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /*private static List<String> REQUEST_URLS = Collections.EMPTY_LIST;
    private List<Request> createRequests(){
        // init request
        int requestSize = 1;
        REQUEST_URLS = new ArrayList<>(requestSize);
        List<Request> requests = new ArrayList<>();

        for (int i = 0; i < requestSize; i++) {

            long minBeHostTime = System.currentTimeMillis() / 1000 - + 1200 + new Random().nextInt(10000);
            long iTime = System.currentTimeMillis() / 1000 - 1200 + new Random().nextInt(10000);

            // max_behot_time=1532066832&i=1532066832&
            String url = String.format("%s&max_behot_time=%s&i=%s", url, minBeHostTime, iTime);
            REQUEST_URLS.add(url);
            Request request = new Request(url);
            addHeaders(request);
            requests.add(request);
        }

    }*/

    @Override
    public void process(Page page) {
        String pageResponse = page.getRawText();
//        System.out.println(pageResponse);
        if(page.getUrl().regex("https://www\\.toutiao\\.com/api/pc/feed/\\S+").match()) {
            System.out.println(pageResponse);
            try {
                JsonNode jsonNode = objectMapper.readTree(pageResponse);
                String message = jsonNode.get("message").asText();
                if ("success".equals(message)) {
                    List<Article> articleInfos = Lists.newArrayList();
                    jsonNode.get("data").elements().forEachRemaining(datum -> {
                        JsonNode titleNode = datum.get("title");
                        JsonNode itemIdNode = datum.get("item_id");
                        JsonNode abstractTextNode = datum.get("abstract");
                        JsonNode imageUrlNode = datum.get("image_url");
                        JsonNode sourceNode = datum.get("source");
                        JsonNode sourceUrlNode = datum.get("source_url");
                        JsonNode tagNode = datum.get("tag");
                        JsonNode chineseTagNode = datum.get("chinese_tag");
                        JsonNode behotTimeNode = datum.get("behot_time");

                        Article articleInfo = new Article();
                        if (null != itemIdNode) {
                            articleInfo.setItemId(itemIdNode.asText());
                        }
                        if (null != titleNode) {
                            articleInfo.setTitle(titleNode.asText());
                        }
                        if (null != abstractTextNode) {
                            articleInfo.setAbstractText(abstractTextNode.asText());
                        }
                        if (null != imageUrlNode) {
                            articleInfo.setCover(imageUrlNode.asText());
                        }
                        if(null != sourceNode) {
                            articleInfo.setSource(sourceNode.asText());
                        }
                        if (null != sourceUrlNode) {
                            articleInfo.setSourceUrl("https://www.toutiao.com" + sourceUrlNode.asText());
                        }
                        if (null != tagNode) {
                            articleInfo.setTag(tagNode.asText());
                        }
                        if (null != chineseTagNode) {
                            articleInfo.setChineseTag(chineseTagNode.asText());
                        }
                        if(null != behotTimeNode) {
                            articleInfo.setDate(new Date(behotTimeNode.asLong()*1000L));
                        }
                        articleInfos.add(articleInfo);

                        System.out.println("文章详情链接：" + articleInfo.getSourceUrl());

                        Request request = new Request();
                        request.addHeader("user-agent", userAgent);
                        request.setUrl(articleInfo.getSourceUrl());

                        page.addTargetRequest(request);
                    });

                    page.putField("articleInfos", articleInfos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (page.getResultItems().get("articleInfos") == null) {
                //skip this page
                page.setSkip(true);
            }
        }else if(page.getUrl().regex("https://www\\.toutiao\\.com/group/\\w+/").match()){
            System.out.println(page.getRawText());
            Matcher matcher = Pattern.compile("\\d+").matcher(page.getUrl().toString());
            if(matcher.find()) {
                String itemId = matcher.group();
                Map<String, Object> articleInfo = Maps.newHashMap();
                articleInfo.put("itemId", itemId);

                String content = page.getHtml().xpath("//div[@class='article-page']/div[@class='container']/div[@class='main clear']/div[@class='m-content']/div[@class='content-a']/div[@class='a-con']/html()").get();
                if(null == content){
                    content = page.getHtml().xpath("//article/html()").toString();
                }
                if(null == content) {
                    List<String> scripts = page.getHtml().xpath("//script").all();
                    String script = scripts.stream().filter(s -> s.contains("articleInfo:")).findFirst().orElse(null);
                    if(null != script) {
                        script = script.substring(script.indexOf("{"), script.lastIndexOf("}")+1);

                        ScriptObjectMirror result = JavaScriptUtil.eval("var data = "+script, "data");

                        if(null != result && result.containsKey("articleInfo")) {
//                            Map<String, Object> map = JavaScriptUtil.scriptObjectMap(result);
//                        System.out.println(map);
                            content = ((ScriptObjectMirror)result.get("articleInfo")).get("content").toString();
                        }
                    }
                }

                if(null != content) {
                    articleInfo.put("content", content);
                    page.putField("articleInfo", articleInfo);
                    System.out.println(content);
                }
            }
        }
    }

    @Override
    public Site getSite() {
        this.addHeaders(this.site);
        return this.site;
    }

    private void addHeaders(Site request) {
//        request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
//        request.addHeader("Accept-Encoding", "gzip, deflate, br");
//        request.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
//        request.addHeader("Cache-Control", "no-cache");
//        request.addHeader("Connection", "keep-alive");
//        request.addHeader("Cookie", "csrftoken=43b86ecf470c994559f0c8c202a82f83; __tea_sdk__ssid=a3bc9154-debd-4158-97a7-b3af1e2f8969; _ga=GA1.2.1311131223.1550483498; uuid=\"w:cc608770c9c0411aa0fd785f48001365\"; tt_webid=6738181057873069582; WEATHER_CITY=%E5%8C%97%E4%BA%AC; tt_webid=6738181057873069582; s_v_web_id=99594f6c77cd15b6f294e2fbc0262ac3; __tasessionId=tuzqx4o6h1568857512854");
//        request.addHeader("Host", "www.toutiao.com");
//        request.addHeader("Pragma", "no-cache");
//        request.addHeader("Upgrade-Insecure-Requests", "1");
        request.addHeader("User-Agent", userAgent);
    }

    public void run(){

        Spider.create(new ToutiaoPageProcessor()).addUrl(url).addPipeline(this.articlePipeline).thread(5).run();
    }

}

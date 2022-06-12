package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.util.HashSet;

@SpringBootApplication
public class WebCrawlerApplication {
    private HashSet<String> urlLink;

    public WebCrawlerApplication() {
        urlLink = new HashSet<>();
    }

    public void getPageLinks(String URL) {
        if (!urlLink.contains(URL)) {
            try {
                if (urlLink.add(URL)) {
                    System.out.println(URL);
                }
                Document doc = Jsoup.connect(URL).get();
                Elements availableLinksOnPage = doc.select("a[href]");
                for (Element ele : availableLinksOnPage) {
                    getPageLinks(ele.attr("abs:href"));
                }
            }
            catch (IOException e) {
                // print exception messages
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(WebCrawlerApplication.class, args);
        WebCrawlerApplication obj = new WebCrawlerApplication();
        obj.getPageLinks("https://google.com/");
    }

}

package com.dibyojyoti.demo;

import com.dibyojyoti.demo.crawler.WebCrawler;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java WebCrawler <start-url>");
            return;
        }

        try {
            String startUrl = args[0];
            WebCrawler crawler = new WebCrawler(startUrl);
            crawler.start();
        } catch (URISyntaxException e) {
            System.err.println("Invalid URL format: " + args[0]);
        }
    }
}

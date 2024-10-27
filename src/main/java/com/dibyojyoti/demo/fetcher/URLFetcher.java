package com.dibyojyoti.demo.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class URLFetcher {
    public Set<String> fetch(String url) {
        Set<String> urls = new HashSet<>();
        try {
            Document doc = getDocument(url);
            Elements links = doc.select("a[href]");
            links.forEach(link -> urls.add(link.absUrl("href")));
        } catch (IOException e) {
            System.err.println("Error accessing " + url + ": " + e.getMessage());
        }
        return urls;
    }

    protected Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}

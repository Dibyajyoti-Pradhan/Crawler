package com.dibyojyoti.demo.crawler;

import com.dibyojyoti.demo.fetcher.URLFetcher;
import com.dibyojyoti.demo.filter.Filter;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;

public class CrawlerTask implements Callable<Void> {
    private final String url;
    private final Set<String> visitedUrls;
    private final Queue<String> urlsToVisit;
    private final List<Filter> filters;
    private final URLFetcher urlFetcher;

    public CrawlerTask(String url, Set<String> visitedUrls, Queue<String> urlsToVisit, List<Filter> filters,
            URLFetcher urlFetcher) {
        this.url = url;
        this.visitedUrls = visitedUrls;
        this.urlsToVisit = urlsToVisit;
        this.filters = filters;
        this.urlFetcher = urlFetcher;
    }

    @Override
    public Void call() {
        if (visitedUrls.add(url)) {
            System.out.println("Visited: " + url);
            Set<String> newUrls = urlFetcher.fetch(url);
            newUrls.forEach(linkUrl -> {
                if (!visitedUrls.contains(linkUrl) && filters.stream().allMatch(filter -> filter.isValid(linkUrl))) {
                    urlsToVisit.add(linkUrl);
                }
            });
        }
        return null;
    }
}

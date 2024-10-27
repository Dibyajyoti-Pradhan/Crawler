package com.dibyojyoti.demo.crawler;

import com.dibyojyoti.demo.fetcher.URLFetcher;
import com.dibyojyoti.demo.filter.DomainFilter;
import com.dibyojyoti.demo.filter.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebCrawlerTest {
    private WebCrawler webCrawler;
    private String startUrl;
    private Filter domainFilter;
    private URLFetcher urlFetcher;
    private Queue<String> urlsToVisit;

    @BeforeEach
    public void setup() throws URISyntaxException {
        startUrl = "https://monzo.com";
        domainFilter = mock(DomainFilter.class);
        urlFetcher = mock(URLFetcher.class);
        urlsToVisit = new ConcurrentLinkedQueue<>();
        urlsToVisit.add(startUrl);

        webCrawler = new WebCrawler(startUrl, List.of(domainFilter), urlFetcher, urlsToVisit);
    }

    @Test
    public void testStart() throws URISyntaxException, InterruptedException {
        when(domainFilter.isValid(any())).thenReturn(true);
        when(urlFetcher.fetch(startUrl)).thenReturn(Set.of("https://monzo.com/about", "https://monzo.com/features"));

        webCrawler.start();

        // Allow some time for tasks to complete
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(webCrawler.visitedUrls.contains(startUrl));
        assertTrue(webCrawler.visitedUrls.contains("https://monzo.com/about"));
        assertTrue(webCrawler.visitedUrls.contains("https://monzo.com/features"));
    }
}

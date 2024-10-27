package com.dibyojyoti.demo.crawler;

import com.dibyojyoti.demo.fetcher.URLFetcher;
import com.dibyojyoti.demo.filter.DomainFilter;
import com.dibyojyoti.demo.filter.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CrawlerTaskTest {
    private Set<String> visitedUrls;
    private Queue<String> urlsToVisit;
    private Filter domainFilter;
    private URLFetcher urlFetcher;
    private String testUrl;

    @BeforeEach
    public void setup() throws URISyntaxException {
        visitedUrls = new HashSet<>();
        urlsToVisit = new ConcurrentLinkedQueue<>();
        testUrl = "https://monzo.com/";
        domainFilter = new DomainFilter(testUrl);
        urlFetcher = mock(URLFetcher.class);
    }

    @Test
    public void testCall() throws Exception {
        CrawlerTask crawlerTask = new CrawlerTask(testUrl, visitedUrls, urlsToVisit, List.of(domainFilter), urlFetcher);

        Set<String> fetchedUrls = new HashSet<>();
        fetchedUrls.add("https://monzo.com/about");
        fetchedUrls.add("https://monzo.com/features");
        when(urlFetcher.fetch(testUrl)).thenReturn(fetchedUrls);

        crawlerTask.call();

        assertTrue(visitedUrls.contains(testUrl));
        assertTrue(urlsToVisit.containsAll(fetchedUrls));
    }
}

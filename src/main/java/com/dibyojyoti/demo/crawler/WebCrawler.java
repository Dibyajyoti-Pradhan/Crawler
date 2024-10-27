package com.dibyojyoti.demo.crawler;

import com.dibyojyoti.demo.fetcher.URLFetcher;
import com.dibyojyoti.demo.filter.Filter;
import com.dibyojyoti.demo.filter.DomainFilter;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

public class WebCrawler {
    final Set<String> visitedUrls;
    final Queue<String> urlsToVisit;
    final List<Filter> filters;
    final URLFetcher urlFetcher;
    final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    private static final int THREAD_COUNT = 10;

    // Constructor for production use
    public WebCrawler(String startUrl) throws URISyntaxException {
        this.visitedUrls = ConcurrentHashMap.newKeySet();
        this.urlsToVisit = new ConcurrentLinkedQueue<>();
        this.urlsToVisit.add(startUrl);
        this.filters = Arrays.asList(new DomainFilter(startUrl));
        this.urlFetcher = new URLFetcher();
    }

    // Constructor for testing with dependency injection
    public WebCrawler(String startUrl, List<Filter> filters, URLFetcher urlFetcher,
            Queue<String> urlsToVisit) {
        this.visitedUrls = ConcurrentHashMap.newKeySet();
        this.urlsToVisit = urlsToVisit;
        this.filters = filters;
        this.urlFetcher = urlFetcher;
    }

    public void start() {
        while (!urlsToVisit.isEmpty() || !((ThreadPoolExecutor) executorService).getQueue().isEmpty()) {
            while (!urlsToVisit.isEmpty()) {
                String url = urlsToVisit.poll();
                if (url != null && !visitedUrls.contains(url)) {
                    CrawlerTask task = new CrawlerTask(url, visitedUrls, urlsToVisit, filters, urlFetcher);
                    executorService.submit(task);
                }
            }

            // Wait for some time to allow threads to process the tasks
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Check for completion
            if (urlsToVisit.isEmpty() && ((ThreadPoolExecutor) executorService).getActiveCount() == 0) {
                break;
            }
        }
        shutdownAndAwaitTermination();
    }

    private void shutdownAndAwaitTermination() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("ExecutorService did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

package com.dibyojyoti.demo.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class URLFetcherTest {

    @Test
    public void testFetch() throws IOException {
        URLFetcher urlFetcher = Mockito.spy(new URLFetcher());
        Document mockDoc = Jsoup.parse("<html><body><a href='https://monzo.com/about'>About</a></body></html>");
        doReturn(mockDoc).when(urlFetcher).getDocument(anyString());

        Set<String> urls = urlFetcher.fetch("https://monzo.com");

        assertTrue(urls.contains("https://monzo.com/about"));
    }

    @Test
    public void testFetchWithIOException() throws IOException {
        URLFetcher urlFetcher = Mockito.spy(new URLFetcher());
        doThrow(new IOException("Invalid URL")).when(urlFetcher).getDocument(anyString());

        Set<String> urls = urlFetcher.fetch("https://invalid-url.com");

        assertTrue(urls.isEmpty());
    }
}

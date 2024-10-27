package com.dibyojyoti.demo.filter;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DomainFilterTest {

    @Test
    public void testIsValid() throws URISyntaxException {
        String startUrl = "https://monzo.com";
        DomainFilter domainFilter = new DomainFilter(startUrl);

        assertTrue(domainFilter.isValid("https://monzo.com/features"));
        assertFalse(domainFilter.isValid("https://facebook.com/monzo"));
        assertFalse(domainFilter.isValid("https://community.monzo.com"));
    }

    @Test
    public void testGetDomainName() throws URISyntaxException {
        String startUrl = "https://www.monzo.com";
        DomainFilter domainFilter = new DomainFilter(startUrl);
        assertTrue(domainFilter.isValid("https://monzo.com/about"));
    }
}

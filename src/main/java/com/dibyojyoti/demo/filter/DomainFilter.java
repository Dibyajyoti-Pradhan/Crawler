package com.dibyojyoti.demo.filter;

import java.net.URI;
import java.net.URISyntaxException;

public class DomainFilter implements Filter {
    private final String domain;
    private final int domainParts;

    public DomainFilter(String startUrl) throws URISyntaxException {
        this.domain = getDomainName(startUrl);
        this.domainParts = this.domain.split("\\.").length;
    }

    @Override
    public boolean isValid(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            if (host == null) {
                return false;
            }
            String cleanHost = host.startsWith("www.") ? host.substring(4) : host;
            return cleanHost.equals(domain)
                    || (cleanHost.endsWith(domain) && cleanHost.split("\\.").length == domainParts);
        } catch (Exception e) {
            return false;
        }
    }

    private String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain != null ? (domain.startsWith("www.") ? domain.substring(4) : domain) : null;
    }
}

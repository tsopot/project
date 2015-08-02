package com.project.httpserver.models;

import java.util.List;
import java.util.Map;

public class Status {

    private int totalCount;
    private int uniqueCount;
    private List<Map<String, String>> ipRequests;
    private List<Map<String, String>> urlRedirects;
    private List<Request> lastRequests;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getUniqueCount() {
        return uniqueCount;
    }

    public void setUniqueCount(int uniqueCount) {
        this.uniqueCount = uniqueCount;
    }

    public List<Map<String, String>> getIpRequests() {
        return ipRequests;
    }

    public void setIpRequests(List<Map<String, String>> ipRequests) {
        this.ipRequests = ipRequests;
    }

    public List<Map<String, String>> getUrlRedirects() {
        return urlRedirects;
    }

    public void setUrlRedirects(List<Map<String, String>> urlRedirects) {
        this.urlRedirects = urlRedirects;
    }

    public List<Request> getLastRequests() {
        return lastRequests;
    }

    public void setLastRequests(List<Request> lastRequests) {
        this.lastRequests = lastRequests;
    }
}


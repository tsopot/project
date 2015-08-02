package com.project.httpserver.models;

import java.sql.Timestamp;
import java.util.Date;

public class Request {

    private String ip;
    private String uri;
    private Date time;
    private long duration;
    private String urlRedirect;
    private long receivedBytes;
    private long sentBytes;
    private Timestamp timestamp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) { this.time = time; }

    public void setDuration(long duration) { this.duration = duration; }

    public String getUrlRedirect() {
        return urlRedirect;
    }

    public void setUrlRedirect(String urlRedirect) {
        this.urlRedirect = urlRedirect;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }

    public void setReceivedBytes(long receivedBytes) {
        this.receivedBytes = receivedBytes;
    }

    public long getSentBytes() { return sentBytes; }

    public void setSentBytes(long sentBytes) {
        this.sentBytes = sentBytes;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getSpeed() {
        double result;

        double totalData = (receivedBytes + sentBytes) * 1000;
        result = (duration > 0) ? (totalData / duration) : 0;

        return result;
    }
}


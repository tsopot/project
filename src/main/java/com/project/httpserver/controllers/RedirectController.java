package com.project.httpserver.controllers;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.LOCATION;
import static io.netty.handler.codec.http.HttpResponseStatus.FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class RedirectController implements Controller {

    private String urlRedirect;

    public RedirectController(String urlRedirect) {
        this.urlRedirect = urlRedirect;
    }

    @Override
    public FullHttpResponse getResponse() throws InterruptedException {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FOUND);

        response.headers().set(LOCATION, urlRedirect)
                          .set(CONTENT_TYPE, "text/html; charset=UTF-8");
        return response;
    }

}

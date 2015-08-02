package com.project.httpserver.controllers;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class NotFoundController implements Controller {

    @Override
    public FullHttpResponse getResponse() throws InterruptedException {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN);

        response.headers().set(CONTENT_TYPE, "text/html")
                          .set(CONTENT_LENGTH, response.content().readableBytes());

        return response;
    }
}

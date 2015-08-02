package com.project.httpserver.controllers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HelloController implements Controller {

    private static final String MESSAGE = "Hello World";
    private static final ByteBuf CONTENT = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(MESSAGE, CharsetUtil.UTF_8));

    @Override
    public FullHttpResponse getResponse() throws InterruptedException {

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, CONTENT.duplicate());

        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8")
                          .set(CONTENT_LENGTH, response.content().readableBytes());

        return response;
    }
}

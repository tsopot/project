package com.project.httpserver.controllers;

import io.netty.handler.codec.http.FullHttpResponse;

public interface Controller {

    FullHttpResponse getResponse() throws InterruptedException;

}

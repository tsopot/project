package com.project.httpserver.controllers;

import com.project.httpserver.dao.DaoFactory;
import com.project.httpserver.dao.RequestJdbcDao;
import com.project.httpserver.models.Status;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import static com.project.httpserver.pages.StatusPage.getContent;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class StatusController implements Controller {

    @Override
    public FullHttpResponse getResponse() throws InterruptedException {

        RequestJdbcDao jdbcDao = DaoFactory.getRequestJdbcDao();

        Status status = jdbcDao.getStatus();

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, getContent(status));

        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8")
                          .set(CONTENT_LENGTH, response.content().readableBytes());

        return response;
    }
}

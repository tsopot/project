package com.project.httpserver.server;

import com.project.httpserver.dao.DaoFactory;
import com.project.httpserver.dao.RequestJdbcDao;
import com.project.httpserver.models.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;

import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TrafficShapingHandler extends ChannelTrafficShapingHandler {

    Request request = new Request();

    public TrafficShapingHandler(long checkInterval) {
        super(checkInterval);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String ip = ((InetSocketAddress) ctx.channel().remoteAddress()).getHostString();

        Date time = Calendar.getInstance().getTime();

        request.setIp(ip);
        request.setTime(time);

        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;

            String uri = httpRequest.getUri();
            String url = "empty";

            if (!url.matches("http://.*")) {
                if (!url.matches("www\\..*")) {
                    url = "http://www." + url;
                } else {
                    url = "http://" + url;
                }
            }

            request.setUri(uri);
            request.setUrlRedirect(url);
        }

        super.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        trafficAccounting();

        if (request.getUri() == null) {
            request.setUri("empty");
        }

        super.channelInactive(ctx);
    }

    private void trafficAccounting() {
        RequestJdbcDao jdbcDao = DaoFactory.getRequestJdbcDao();

        TrafficCounter counter = trafficCounter();

        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

        request.setReceivedBytes(Math.abs(counter.cumulativeReadBytes()));
        request.setSentBytes(counter.cumulativeWrittenBytes());
        request.setTimestamp(timestamp);

        jdbcDao.insertRequest(request);
    }
}

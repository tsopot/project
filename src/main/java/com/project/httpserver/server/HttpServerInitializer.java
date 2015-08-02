package com.project.httpserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("codec", new HttpServerCodec())
                .addLast("traffic", new TrafficShapingHandler(AbstractTrafficShapingHandler.DEFAULT_CHECK_INTERVAL))
                .addLast("handler", new HttpServerHandler());
    }
}

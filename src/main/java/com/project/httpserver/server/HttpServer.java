package com.project.httpserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {

    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {


        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                     .group(bossGroup, workerGroup)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new HttpServerInitializer());

            Channel channel = bootstrap.bind(port).sync().channel();

            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port;

        if (args.length > 0) {
                port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }

        new HttpServer(port).run();
    }
}

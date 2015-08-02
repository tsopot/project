package com.project.httpserver.server;

import com.project.httpserver.controllers.Controller;
import com.project.httpserver.controllers.ControllerFactory;
import com.project.httpserver.controllers.HelloController;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpRequest> {

    private static DefaultChannelGroup activeChannels = new DefaultChannelGroup("netty-receiver", ImmediateEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        activeChannels.add(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
        if (is100ContinueExpected(req)) {
            ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
        }

        String uri = req.getUri().toLowerCase();

        Controller controller = ControllerFactory.getController(uri);

        FullHttpResponse response = controller.getResponse();

        if (controller instanceof HelloController) {
            ctx.executor().schedule(() -> ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE), 10, TimeUnit.SECONDS);
        } else {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public static long getConnectionsQuantity() {
        return activeChannels.size();
    }

}

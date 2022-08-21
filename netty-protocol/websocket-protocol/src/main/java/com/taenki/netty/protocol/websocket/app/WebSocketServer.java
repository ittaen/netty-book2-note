package com.taenki.netty.protocol.websocket.app;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.example.http.websocketx.server.WebSocketServerHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

/**
 * WebSocketServer
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 14:14
 */
public class WebSocketServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new WebSocketServer().run(port);
    }

    public void run(final int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast("http-codec", new HttpServerCodec());
                            channel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
                            channel.pipeline().addLast("xml-chunked", new ChunkedWriteHandler());
                            channel.pipeline().addLast("handler", new WebSocketServerHandler());
                        }
                    });
            ChannelFuture future = b.bind(new InetSocketAddress(port)).sync();
            System.out.println("Web socket server started at ：" + "http://127.0.0.1:" + port);
            System.out.println("Open your browser and navigate to http://localhost:" + port + '/');
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}

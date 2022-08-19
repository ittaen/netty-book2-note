package com.taenki.netty.codec.message.pack;

import com.taenki.netty.codec.message.pack.handler.MessagePackDecoder;
import com.taenki.netty.codec.message.pack.handler.MessagePackEncoder;
import com.taenki.netty.codec.vs.java.serial.pojo.UserInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.msgpack.MessagePack;

import java.util.concurrent.TimeUnit;

/**
 * netty 原生 + {@link MessagePack} echo 示例
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 10:44
 */
public class EchoDemo {

    public static void main(String[] args) throws InterruptedException {
        final int port = 8080;
        new Thread(() -> {
            try {
                new EchoServer().bind(port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "EchoServer").start();
        TimeUnit.SECONDS.sleep(2);
        new Thread(() -> {
            try {
                new EchoClient().connect(port, "127.0.0.1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "EchoClient").start();
    }

    static class EchoServer {

        public void bind(int port) throws Exception {
            // 配置服务端的NIO线程组
            NioEventLoopGroup bossGroup = new NioEventLoopGroup();
            NioEventLoopGroup workGroup = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
            try {
                b
                        .group(bossGroup, workGroup)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 1024)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                channel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                                channel.pipeline().addLast("msgPack decoder", new MessagePackDecoder());
                                channel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                                channel.pipeline().addLast("msgPack encoder", new MessagePackEncoder());
                                channel.pipeline().addLast(new EchoServerHandler());
                            }
                        });
                // 绑定端口，同步等待成功
                ChannelFuture f = b.bind(port).sync();

                // 等待服务端监听端口关闭
                f.channel().closeFuture().sync();
            } finally {
                // 优雅退出，释放线程池资源
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        }
    }

    static class EchoClient {

        public void connect(int port, String host) throws Exception {
            // 配置客户端NIO线程组
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            try {
                b
                        .group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                channel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                                channel.pipeline().addLast("msgPack decoder", new MessagePackDecoder());
                                channel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                                channel.pipeline().addLast("msgPack encoder", new MessagePackEncoder());
                                channel.pipeline().addLast(new EchoClientHandler(1000));
                            }
                        });
                // 发起异步连接操作
                ChannelFuture f = b.connect(host, port).sync();

                // 等待客户端链路关闭
                f.channel().closeFuture().sync();
            } finally {
                // 优雅退出，释放NIO线程组
                group.shutdownGracefully();
            }
        }
    }

    static class EchoClientHandler extends ChannelHandlerAdapter {

        private final int sendNumber;

        EchoClientHandler(int sendNumber) {
            this.sendNumber = sendNumber;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            UserInfo[] infos = userInfo();
            //Stream.of(infos).forEach(ctx::write);
            for (UserInfo userInfo : infos) {
                ctx.write(userInfo);
            }
            ctx.flush();
        }

        private UserInfo[] userInfo() {
            UserInfo[] infos = new UserInfo[sendNumber];
            UserInfo userInfo;
            for (int i = 0; i < sendNumber; i++) {
                userInfo = new UserInfo();
                userInfo.setUsername("ABCDEFG ----> " + i);
                userInfo.setUserId(i);
                infos[i] = userInfo;
            }
            return infos;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("Client receive the msgPack msg : " + msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }
    }

    static class EchoServerHandler extends ChannelHandlerAdapter {

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            // 发生异常，关闭链路
            ctx.close();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("Server receive the msgPack msg : " + msg);
            ctx.writeAndFlush(msg);
        }

    }

}

package com.taenki.aio;

import java.util.concurrent.TimeUnit;

/**
 * aio TimeClient 示例
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/18 9:31
 */
public class TimeClient {

    public static void main(String[] args) {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
                run(args);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void run(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // 采用默认值
            }
        }
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
    }
}

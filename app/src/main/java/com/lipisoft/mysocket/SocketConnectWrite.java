package com.lipisoft.mysocket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

class SocketConnectWrite implements Runnable {
    private static final String TAG = "SocketConnectWrite";
    private Handler handler;
    private SocketChannel channel;

    @Override
    public void run() {
        Looper.prepare();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MainActivity.CONNECT:
                        final Boolean blockingMode = (Boolean) msg.obj;
                        connect(blockingMode);
                        break;
                    case MainActivity.WRITE:
                        write();
                        break;
                    default:
                        Log.d(TAG, "Only CONNECT or WRITE message can be handled in SocketConnectWrite Runnable.");
                        break;
                }
            }
        };

        Looper.loop();
    }

    private void connect(boolean blockingMode) {
//        final SocketAddress socket = new InetSocketAddress("m.play.melon.com", 80);
        final SocketAddress socket = new InetSocketAddress("www.google.co.kr", 80);

        try {
            channel = SocketChannel.open(socket);
            channel.configureBlocking(blockingMode);
        } catch (IOException e) {
            Log.e(TAG, "failed to connect.");
        }
    }

    private void write() {
        if (channel != null) {
            final String request = "GET / HTTP/1.1\r\n";
            final String userAgent = "User-Agent: Android 6.0.1; SM-G900S\r\n";
//            final String host = "Host: m.play.melon.com\r\n";
            final String host = "Host: www.google.co.kr\r\n";
//            final String connectionMode = "Connection: Keep-Alive\r\n";
            final String end = "\r\n";

//            final String message = request + userAgent + host + connectionMode + end;
            final String httpHeader = request + userAgent + host + end;

            final ByteBuffer httpGet = ByteBuffer.allocate(httpHeader.length());
            httpGet.put(httpHeader.getBytes());
            httpGet.flip();

            try {
                channel.write(httpGet);
            } catch (IOException e) {
                Log.e(TAG, "failed to write.", e);
            }
        } else {
            Log.e(TAG, "SocketChannel is not valid.");
        }
    }

    Handler getHandler() {
        return handler;
    }

    SocketChannel getChannel() {
        return channel;
    }
}

package com.lipisoft.mysocket;

import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


class WriteRunnable implements Runnable {
    private static final String TAG = "WriteRunnable";

    @Override
    public void run() {
        final MySocketManager mySocketManager = MySocketManager.getInstance();
        final SocketChannel channel = mySocketManager.getChannel();

        if (channel != null) {
            final String request = "GET / HTTP/1.1\r\n";
            final String userAgent = "User-Agent: Android 6.0.1; SM-G900S\r\n";
            String host = "Host: m.play.melon.com\r\n";
//            String connectionMode = "Connection: Keep-Alive\r\n";
            String end = "\r\n";

//            String message = request + userAgent + host + connectionMode + end;
            String httpHeader = request + userAgent + host + end;


            ByteBuffer httpGet = ByteBuffer.allocate(2048);
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
}

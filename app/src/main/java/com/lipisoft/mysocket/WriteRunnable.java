package com.lipisoft.mysocket;

import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


class WriteRunnable implements Runnable {
    private static final String TAG = "WriteRunnable";

    @Override
    public void run() {
        String request = "GET / HTTP/1.1\r\n";
        String userAgent = "User-Agent: Android 6.0.1; 4.2.2; SM-G900S\r\n";
        String host = "Host: www.google.co.kr\r\n";
//            String connectionMode = "Connection: Keep-Alive\r\n";
        String end = "\r\n";

//            String message = request + userAgent + host + connectionMode + end;
        String message = request + userAgent + host + end;


        ByteBuffer httpGet = ByteBuffer.allocate(2048);
        httpGet.put(message.getBytes());
        httpGet.flip();

        SocketChannel channel = MySocketManager.getInstance().getChannel();
        try {
            channel.write(httpGet);
        } catch (IOException e) {
            Log.e(TAG, "failed to write.", e);
        }
    }
}

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

class SocketRunnable implements Runnable {
    private static final String TAG = "SocketRunnable";
    private Handler handler;
    private SocketChannel channel;

    @Override
    public void run() {
        Looper.prepare();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Boolean blockingMode = (Boolean) msg.obj;

                switch (msg.what) {
                    case MainActivity.CONNECT:
                        connect(blockingMode);
                        break;
                    case MainActivity.WRITE:
                        write();
                        break;
                    case MainActivity.READ:
                        read();
                        break;
                    case MainActivity.CLOSE:
                        close();
                        break;
                }
            }
        };

        Looper.loop();
    }

    private void connect(boolean blockingMode) {
        final SocketAddress socket = new InetSocketAddress("m.play.melon.com", 80);

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
            final String host = "Host: m.play.melon.com\r\n";
//            final String connectionMode = "Connection: Keep-Alive\r\n";
            final String end = "\r\n";

//            final String message = request + userAgent + host + connectionMode + end;
            final String httpHeader = request + userAgent + host + end;


            final ByteBuffer httpGet = ByteBuffer.allocate(2048);
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

    private void read() {
        if (channel != null) {
            final ByteBuffer resHttp = ByteBuffer.allocate(2048);
            try {
                int numberReceived = channel.read(resHttp);

                while (numberReceived > 0) {
                    resHttp.flip();

                    final byte[] response = new byte[numberReceived];
                    resHttp.get(response);

                    Log.d(TAG, new String(response));

                    resHttp.clear();
                    numberReceived = channel.read(resHttp);
                }
                Log.d(TAG, "Received Bytes: " + numberReceived);

                // When the remote server sends FIN,
                if (numberReceived == -1) {
                    channel.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "failed to read.");
            }
        } else {
            Log.e(TAG, "SocketChannel is not valid.");
        }
    }

    private void close() {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                Log.e(TAG, "failed to close.", e);
            }
        } else {
            Log.e(TAG, "SocketChannel is not valid.");
        }
    }

    Handler getHandler() {
        return handler;
    }
}

package com.lipisoft.mysocket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

class SocketReadClose implements Runnable {
    private static final String TAG = "SocketReadClose";
    private Handler handler;
    private SocketChannel channel;

    @Override
    public void run() {
        Looper.prepare();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MainActivity.READ:
                        channel = (SocketChannel) msg.obj;
                        read();
                        break;
                }
            }
        };

        Looper.loop();
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

    Handler getHandler() {
        return handler;
    }
}

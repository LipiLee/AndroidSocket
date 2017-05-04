package com.lipisoft.mysocket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.nio.channels.SocketChannel;

class SocketClose implements Runnable {
    private static final String TAG = "SocketClose";
    private Handler handler;
    private SocketChannel channel;

    @Override
    public void run() {
        Looper.prepare();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MainActivity.CLOSE:
                        channel = (SocketChannel) msg.obj;
                        close();
                        break;
                    default:
                        Log.d(TAG, "Only CLOSE message can be handled in SocketClose Runnable.");
                        break;
                }
            }
        };

        Looper.loop();
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

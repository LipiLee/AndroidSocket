package com.lipisoft.mysocket;

import android.util.Log;

import java.io.IOException;
import java.nio.channels.SocketChannel;

class CloseRunnable implements Runnable {
    private static final String TAG = "CloseRunnable";

    @Override
    public void run() {
        SocketChannel channel = MySocketManager.getInstance().getChannel();
        try {
            channel.close();
        } catch (IOException e) {
            Log.e(TAG, "failed to close.", e);
        }
    }
}

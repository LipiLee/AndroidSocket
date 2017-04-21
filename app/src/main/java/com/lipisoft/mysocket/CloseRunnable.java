package com.lipisoft.mysocket;

import android.util.Log;

import java.io.IOException;
import java.nio.channels.SocketChannel;

class CloseRunnable implements Runnable {
    private static final String TAG = "CloseRunnable";

    @Override
    public void run() {
        final MySocketManager mySocketManager = MySocketManager.getInstance();
        final SocketChannel channel = mySocketManager.getChannel();

        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                Log.e(TAG, "failed to close.", e);
            }
            mySocketManager.setChannel(null);
        } else {
            Log.e(TAG, "SocketChannel is not valid.");
        }
    }
}

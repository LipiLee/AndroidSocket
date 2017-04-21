package com.lipisoft.mysocket;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

class ConnectRunnable implements Runnable {
    private static final String TAG = "ConnectRunnable";

    @Override
    public void run() {
        final SocketAddress socket = new InetSocketAddress("www.google.com", 80);

        try {
            final SocketChannel channel = SocketChannel.open(socket);
            MySocketManager.getInstance().setChannel(channel);
        } catch (IOException e) {
            Log.e(TAG, "failed to connect.");
        }
    }
}

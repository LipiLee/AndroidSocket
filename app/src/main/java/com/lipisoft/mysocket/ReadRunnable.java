package com.lipisoft.mysocket;

import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

class ReadRunnable implements Runnable {
    private static final String TAG = "ReadRunnable";

    @Override
    public void run() {
        ByteBuffer resHttp = ByteBuffer.allocate(2048);
        SocketChannel channel = MySocketManager.getInstance().getChannel();
        try {
            channel.read(resHttp);
        } catch (IOException e) {
            Log.e(TAG, "failed to read.");
        }
        resHttp.flip();
        byte[] response = new byte[40];
        resHttp.get(response);
        Log.d(TAG, new String(response));
    }
}

package com.lipisoft.mysocket;

import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

class ReadRunnable implements Runnable {
    private static final String TAG = "ReadRunnable";

    @Override
    public void run() {
        final MySocketManager mySocketManager = MySocketManager.getInstance();
        final SocketChannel channel = mySocketManager.getChannel();

        if (channel != null) {
            final ByteBuffer resHttp = ByteBuffer.allocate(2048);
            try {
                channel.read(resHttp);
            } catch (IOException e) {
                Log.e(TAG, "failed to read.");
            }
            resHttp.flip();

            final byte[] response = new byte[2048];
            resHttp.get(response);

            Log.d(TAG, new String(response));
        } else {
            Log.e(TAG, "SocketChannel is not valid.");
        }
    }
}

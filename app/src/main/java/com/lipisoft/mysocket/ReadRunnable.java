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
}

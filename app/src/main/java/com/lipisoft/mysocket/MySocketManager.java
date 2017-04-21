package com.lipisoft.mysocket;

import java.nio.channels.SocketChannel;

class MySocketManager {
    private static final MySocketManager instance = new MySocketManager();
    private SocketChannel channel;
    private boolean blockingMode = false;

    static MySocketManager getInstance() {
        return instance;
    }

    SocketChannel getChannel() {
        return channel;
    }

    void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    boolean isBlockingMode() {
        return blockingMode;
    }

    void setBlockingMode(boolean blockingMode) {
        this.blockingMode = blockingMode;
    }
}

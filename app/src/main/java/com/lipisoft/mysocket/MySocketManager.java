package com.lipisoft.mysocket;

import java.nio.channels.SocketChannel;

class MySocketManager {
    private static final MySocketManager instance = new MySocketManager();
    private SocketChannel channel;

    static MySocketManager getInstance() {
        return instance;
    }

    SocketChannel getChannel() {
        return channel;
    }

    void setChannel(SocketChannel channel) {
        this.channel = channel;
    }
}

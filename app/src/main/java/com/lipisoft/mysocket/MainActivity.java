package com.lipisoft.mysocket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.nio.channels.SocketChannel;

public class MainActivity extends AppCompatActivity {
    public static final int CONNECT = 0;
    public static final int WRITE = 1;
    public static final int READ = 2;
    public static final int CLOSE = 3;

    private static boolean isBlockingMode;
    private static final SocketConnectWrite writeRunnable = new SocketConnectWrite();
    private static final SocketReadClose readRunnable = new SocketReadClose();
    private static final SocketClose closeRunnable = new SocketClose();

    private static Handler writeHandler;
    private static Handler readHandler;
    private static Handler closeHandler;

    private static SocketChannel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch toggle = (Switch) findViewById(R.id.switch1);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                isBlockingMode = isChecked;
            }
        });

        final Thread writeThread = new Thread(writeRunnable);
        writeThread.start();

        final Thread readThread = new Thread(readRunnable);
        readThread.start();

        final Thread closeThread = new Thread(closeRunnable);
        closeThread.start();
    }

    public void onConnect(View v) {
        writeHandler = writeRunnable.getHandler();
        final Message message = writeHandler.obtainMessage(CONNECT, isBlockingMode);
        message.sendToTarget();
    }

    public void onWrite(View v) {
        channel = writeRunnable.getChannel();
        final Message message = writeHandler.obtainMessage(WRITE);
        message.sendToTarget();
    }

    public void onRead(View v) {
        readHandler = readRunnable.getHandler();
        final Message message = readHandler.obtainMessage(READ, channel);
        message.sendToTarget();
    }

    public void onClose(View v) {
        closeHandler = closeRunnable.getHandler();
        final Message message = closeHandler.obtainMessage(CLOSE, channel);
        message.sendToTarget();
    }
}

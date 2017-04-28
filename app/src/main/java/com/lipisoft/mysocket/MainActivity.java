package com.lipisoft.mysocket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    public static final int CONNECT = 0;
    public static final int WRITE = 1;
    public static final int READ = 2;
    public static final int CLOSE = 3;

    private static boolean isBlockingMode;
    private static final SocketRunnable runnable = new SocketRunnable();
    private static Handler handler;

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

        final Thread myThread = new Thread(runnable);
        myThread.start();
    }

    public void onConnect(View v) {
        handler = runnable.getHandler();
        final Message message = handler.obtainMessage(CONNECT, isBlockingMode);
        message.sendToTarget();
    }

    public void onWrite(View v) {
        final Message message = handler.obtainMessage(WRITE, isBlockingMode);
        message.sendToTarget();
    }

    public void onRead(View v) {
        final Message message = handler.obtainMessage(READ, isBlockingMode);
        message.sendToTarget();
    }

    public void onClose(View v) {
        final Message message = handler.obtainMessage(CLOSE, isBlockingMode);
        message.sendToTarget();
    }
}

package com.lipisoft.mysocket;

import android.os.Bundle;
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

    private boolean isBlockingMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch toggle = (Switch) findViewById(R.id.switch1);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
//                MySocketManager.getInstance().setBlockingMode(isChecked);
                isBlockingMode = isChecked;
            }
        });

        final Thread myThread = new Thread(new SocketRunnable());
        myThread.start();
    }

    public void onConnect(View v) {
        final Message message = SocketRunnable.handler.obtainMessage(CONNECT, isBlockingMode);
        message.sendToTarget();
    }

    public void onWrite(View v) {
        final Message message = SocketRunnable.handler.obtainMessage(WRITE, isBlockingMode);
        message.sendToTarget();
    }

    public void onRead(View v) {
        final Message message = SocketRunnable.handler.obtainMessage(READ, isBlockingMode);
        message.sendToTarget();
    }

    public void onClose(View v) {
        final Message message = SocketRunnable.handler.obtainMessage(CLOSE, isBlockingMode);
        message.sendToTarget();
    }
}

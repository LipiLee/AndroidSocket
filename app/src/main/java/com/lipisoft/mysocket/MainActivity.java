package com.lipisoft.mysocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onConnectThread(View v) {
        final Thread myThread = new Thread(new ConnectRunnable());
        myThread.start();
    }

    public void onWriteThread(View v) {
        final Thread myThread = new Thread(new WriteRunnable());
        myThread.start();
    }

    public void onReadThread(View v) {
        final Thread myThread = new Thread(new ReadRunnable());
        myThread.start();
    }

    public void onCloseThread(View v) {
        final Thread myThread = new Thread(new CloseRunnable());
        myThread.start();
    }
}

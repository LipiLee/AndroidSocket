package com.lipisoft.mysocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "MainActivity";
    private Thread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onConnectThread(View v) {
        myThread = new Thread(new ConnectRunnable());
        myThread.start();
    }

    public void onWriteThread(View v) {
        myThread = new Thread(new WriteRunnable());
        myThread.start();
    }

    public void onReadThread(View v) {
        myThread = new Thread(new ReadRunnable());
        myThread.start();
    }

    public void onCloseThread(View v) {
        myThread = new Thread(new CloseRunnable());
        myThread.start();
    }

/*
    public void onStopThread(View v) {
        try {
            myThread.interrupt();
        } catch (SecurityException e) {
            Log.e(TAG, "Thread failed to stop thread.", e);
        }
    }
*/
}

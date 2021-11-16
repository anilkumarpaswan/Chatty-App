package com.chatty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(Splash_Activity.this, Login_Activity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
public void onPause(){
super.onPause();
finish();
}
}
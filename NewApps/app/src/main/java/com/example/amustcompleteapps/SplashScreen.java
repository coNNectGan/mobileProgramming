package com.example.amustcompleteapps;

import  android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        loadingScreen();
    }

    private void loadingScreen(){
        progressStatus = 0;
        new Thread(new Runnable()
        {
            public void run(){
                while(progressStatus < 100){
                    // Update the progress status
                    progressStatus += 1;
                    //Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    //Update the progress bar

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });

                }

                //Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class)
                Intent intent = new Intent(SplashScreen.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        }).start(); // start the operation
    }
}

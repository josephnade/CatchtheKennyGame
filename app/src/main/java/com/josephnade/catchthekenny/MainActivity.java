package com.josephnade.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
   private TextView timeTextView;
   private TextView scoreTextView;
   private ImageView imageView;
   private LinearLayout linearLayout;
   private Runnable randomDisplayRunnable;
   private Runnable timerRunnable;
   private Handler handler;
   private AlertDialog.Builder alertDialog;

   private Random random;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializing();
        randomDisplayRunnable = () -> {
            displayRandomImage();
            handler.postDelayed(randomDisplayRunnable, 500);

        };
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long l) {
                        timeTextView.setText("Time: " + (l / 1000));
                    }

                    @Override
                    public void onFinish() {
                        showAlertDialog();
                    }
                }.start();
            }
        };
       handler.post(timerRunnable);
        handler.post(randomDisplayRunnable);
       }

    private void initializing() {
        timeTextView = findViewById(R.id.timeTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        imageView = findViewById(R.id.imageView);
        linearLayout = findViewById(R.id.linearLayout);
        alertDialog = new AlertDialog.Builder(this);
        random = new Random();
        handler = new Handler();
        score = 0;
    }
    private void showAlertDialog() {
        alertDialog.setTitle("Restart");
        alertDialog.setMessage("Are you sure to restart game?");
        alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            handler.removeCallbacks(randomDisplayRunnable);
            handler.removeCallbacks(timerRunnable);
            score = 0;
            scoreTextView.setText("Score: "+ score);
            handler.post(timerRunnable);
            handler.post(randomDisplayRunnable);
        });
        alertDialog.setNegativeButton("No", (dialogInterface, i) -> {
            handler.removeCallbacks(randomDisplayRunnable);
            handler.removeCallbacks(timerRunnable);
            imageView.setEnabled(false);
        });
        alertDialog.show();
    }

    public void imageClicked(View view){
        score++;
        scoreTextView.setText("Score:" + score);
    }

    private void displayRandomImage(){
        int containerWidth = linearLayout.getWidth();
        int containerHeight = linearLayout.getHeight();

        int maxX = containerWidth - imageView.getWidth();
        int maxY = containerHeight - imageView.getHeight();

        int randomX = random.nextInt(maxX);
        int randomY = random.nextInt(maxY);

        imageView.setX(randomX);
        imageView.setY(randomY);
    }
}
package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class gameActivity extends AppCompatActivity {

    TextView point , count, counting, question ;
    EditText answer;
    Button b1 ,b2;
    int num1,num2, ans, pt =0 ,life = 3, oneT = 0, x =1;

    Random random =new Random();

    CountDownTimer timer;
    private  static  final long  TIME_IN_MILISECOND = 20000;
    long Time_running = TIME_IN_MILISECOND;
    Boolean timer_bool;

    @SuppressLint({"MissingInflatedId", "WrongViewCast", "SuspiciousIndentation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        point = findViewById(R.id.point);
        count = findViewById(R.id.count);
        counting = findViewById(R.id.counting);
        question = findViewById(R.id.question);

        b1 = findViewById(R.id.bOk);
        b2= findViewById(R.id.bNext);

        answer = findViewById(R.id.editText);

        gamePlay();

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setText("");
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oneT == 0) {
                    int input = Integer.valueOf(answer.getText().toString());
                    if(answer.getText().toString() == ""){
                        input = 0;
                    }
                    if (ans == input) {
                        question.setText("Congratulations , you are right.");
                        pt = pt + 10;
                        point.setText("" + pt);
                        oneT++;
                        pauseTimer();

                    } else {
                        question.setText("Sorry!, you are wrong.");
                        life = life - 1;
                        count.setText("" + life);
                        oneT++;
                        pauseTimer();


                    }
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int i = Integer.valueOf(count.getText().toString());
                if(i == 0){
                    Toast.makeText(gameActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(gameActivity.this,result.class);
                    intent.putExtra("score",point.getText().toString());
                    startActivity(intent);
                    finish();
                }
                resetTimer();
                gamePlay();
                oneT--;
            }
        });
    }

    public void gamePlay(){
        num1 = random.nextInt(999);
        num2 =random.nextInt(999);
        ans = num1 + num2;

        question.setText(num1+ " + " + num2);

        time();
    }

    public void time(){

        timer = new CountDownTimer(Time_running,1000) {
            @Override
            public void onTick(long l) {
                Time_running = l;
                updateText();


            }

            @Override
            public void onFinish() {
                pauseTimer();
                resetTimer();
                updateText();
                life = life - 1;
                count.setText(""+life);
                question.setText("Time is up.");
            }
        }.start();
        timer_bool = true;
    }

    public void updateText(){
        int ct = (int)(Time_running / 1000)%60;
        String time_left = String.format(Locale.getDefault(),"%02d",ct);
        counting.setText(time_left);
    }

    public void pauseTimer(){
        timer.cancel();
        timer_bool = false;
    }

    public void resetTimer(){
        Time_running =TIME_IN_MILISECOND;
    }
}
package com.example.chatbirdfinal.PrimeX;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbirdfinal.R;

import java.util.Timer;
import java.util.TimerTask;

public class PrimeXQuesActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT ="com.example.application.EXTRA_TEXT";

    private EditText input;
    private TextView score,timer;
    private Button confirm,option1,option2,option3,home;
    private LinearLayout optionscontainer;
    private int counter=0;
    private CountDownTimer countdown;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_x_ques);

        input = (EditText) findViewById(R.id.input);
        score = (TextView) findViewById(R.id.prescore);
        timer = (TextView) findViewById(R.id.timer);
        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        confirm = (Button) findViewById(R.id.confirm);
        home = (Button) findViewById(R.id.home);
        optionscontainer = (LinearLayout) findViewById(R.id.optionscontainer);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        showscore();
        score.setText(Integer.toString(counter));
        timer.setText("10");
        input.setEnabled(true);
        enableoptions(false);
        //timerfunction
        countdown = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                reset();
            }
        };
        //when home button is clicked
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        //when confirm button is clicked
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkuserinput();
            }
        });
    }
    //checks whether the user entered a number or not
    public void checkuserinput() {
        if(TextUtils.isEmpty(input.getText().toString())) {
            Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
        }
        else {
            if(Integer.parseInt(input.getText().toString())>4) {
                input.setEnabled(false);
                confirm.setEnabled(false);
                countdown.start();
                generateoptions();
            }
            else {
                Toast.makeText(this, "Enter a number greater than 4", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //generates the value for options
    public void generateoptions() {
        String [] options = new String [3];
        //check how many factors r there
        int userint = Integer.parseInt(input.getText().toString());
        int totalfactors = 0;
        for(int i=2;i<=userint;i++){
            if(userint%i == 0){
                totalfactors++;
            }
        }
        //creates a array of specific length and stores the factors
        String [] factors = new String [totalfactors];
        int k=0;
        for(int i=2;i<=userint;i++){
            if(userint%i == 0){
                factors[k] = Integer.toString(i) ;
                k++;
            }
        }
        //chooses any one factor
        int temp1 = (int)(Math.random()*totalfactors);
        //chooses value for 2nd option
        int temp2;
        int checker1;
        do{
            checker1 = 0;
            temp2 = (int)(Math.random()*(userint-2))+2;
            for (String element : factors) {
                if (element == Integer.toString(temp2)) {
                    checker1 = 1;
                }
            }
        }while (checker1==1);
        //chooses value for 3rd option
        int temp3;
        int checker;
        do{
            checker = 0;
            temp3 = (int)(Math.random()*(userint-2))+2;
            for (String element : factors) {
                if (element == Integer.toString(temp3)) {
                    checker = 1;
                }
            }
        }while (checker==1||temp3==temp2);

        options[0] = factors[temp1];
        final String correctoption = options[0];
        options[1] = Integer.toString(temp2);
        options[2] = Integer.toString(temp3);
        //randomize the options
        for(int i=0 ;i<3 ;i++){
            int j = (int)(Math.random()*3);
            String teme;
            teme = options[i] ;
            options[i] = options[j];
            options[j] = teme;
        }
        enableoptions(true);
        option1.setText(options[0]);
        option2.setText(options[1]);
        option3.setText(options[2]);
        //check which option is clicked
        for(int i=0;i<3;i++){
            optionscontainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countdown.cancel();
                    checkanswer((Button)v,correctoption);
                }
            });
        }

    }
    //checks if the choosen option is correct or wrong
    @SuppressLint("NewApi")
    public void checkanswer(Button selectedoption, String correctoption){

        if(selectedoption.getText().toString().equals(correctoption)){
            selectedoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#88D969")));
            enableoptions(false);
            Timer time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    nextques();
                }
            },800);
        }
        else{
            for(int i=0;i<3;i++){
                optionscontainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            }
            if(option1.getText().toString().equals(correctoption)){
                option1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#88D969")));
            }
            if(option2.getText().toString().equals(correctoption)){
                option2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#88D969")));
            }
            if(option3.getText().toString().equals(correctoption)){
                option3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#88D969")));
            }
            vibrator.vibrate(500);
            enableoptions(false);
            Timer time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    reset();
                }
            },800);
        }
    }
    //enable and disables the options
    public void enableoptions(Boolean enable) {
        for(int i=0;i<3;i++){
            optionscontainer.getChildAt(i).setEnabled(enable);
        }
    }
    //change to next ques
    public void nextques() {
        counter++;
        savescore();
        Intent quizintent = new Intent(PrimeXQuesActivity.this,PrimeXQuesActivity.class);
        startActivity(quizintent);
    }
    //goes to scorepage
    public void reset() {
        counter = 0;
        savescore();
        TextView editText1 = (TextView) findViewById(R.id.prescore);
        String text = editText1.getText().toString();
        Intent quizintent = new Intent(PrimeXQuesActivity.this,PrimeXResultsActivity.class);
        quizintent.putExtra(EXTRA_TEXT,text);
        startActivity(quizintent);
    }
    //saves score
    public void savescore() {
        SharedPreferences sharedpreferences = getSharedPreferences("score",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("score",counter);
        editor.apply();
    }
    //shows score
    public void showscore() {
        SharedPreferences sharedPreferences = getSharedPreferences("score",MODE_PRIVATE);
        counter = sharedPreferences.getInt("score",MODE_PRIVATE);
    }
    @Override
    public void onBackPressed() {
    }
}

package com.example.chatbirdfinal.PrimeX;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatbirdfinal.R;

public class PrimeXResultsActivity extends AppCompatActivity {
    Button home;
    TextView highcore,prescore;
    private int highscore = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_x_results);

        Intent quizintent = getIntent();
        String text = quizintent.getStringExtra(PrimeXQuesActivity.EXTRA_TEXT);

        home = (Button) findViewById(R.id.home);
        highcore = (TextView) findViewById(R.id.highscore);
        prescore = (TextView) findViewById(R.id.prescore);
        showhighscore();
        prescore.setText(text);
        int previousscore = Integer.parseInt(text);
        if(previousscore>highscore){
            highscore = previousscore;
            savehighscore();
        }
        showhighscore();
        String highscorecontent = "High score : " + highscore ;
        highcore.setText(highscorecontent);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmainactivity();
            }
        });
    }
    public void savehighscore() {
        SharedPreferences sharedpreferences = getSharedPreferences("highscore",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("score",highscore);
        editor.apply();
    }
    public void showhighscore() {
        SharedPreferences sharedPreferences = getSharedPreferences("highscore",MODE_PRIVATE);
        highscore = sharedPreferences.getInt("score",MODE_PRIVATE);

    }
    public void openmainactivity() {
        Intent mainintent = new Intent(PrimeXResultsActivity.this,PrimeXHomeActivity.class);
        startActivity(mainintent);
    }
    @Override
    public void onBackPressed() {
    }
}

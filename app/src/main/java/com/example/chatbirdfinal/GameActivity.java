package com.example.chatbirdfinal;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatbirdfinal.Fragment.HomeFragment;
import com.example.chatbirdfinal.PrimeX.PrimeXHomeActivity;
import com.example.chatbirdfinal.PrimeX.PrimeXLeaderboardsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    private Button primex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        primex = (Button) findViewById(R.id.primex);
        setSupportActionBar(toolbar);
        primex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizintent = new Intent(GameActivity.this, PrimeXHomeActivity.class);
                startActivity(quizintent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent quizintent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(quizintent);
    }
}
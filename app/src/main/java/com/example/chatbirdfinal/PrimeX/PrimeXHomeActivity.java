package com.example.chatbirdfinal.PrimeX;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.chatbirdfinal.GameActivity;
import com.example.chatbirdfinal.MainActivity;
import com.example.chatbirdfinal.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chatbirdfinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrimeXHomeActivity extends AppCompatActivity {

    private Button startquiz,leaderboards;
    FirebaseUser user;
    DatabaseReference reference;
    int highscore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_x_home);

        showhighscore();

        savehighscoretofirebase();

        startquiz = (Button) findViewById(R.id.begin);
        leaderboards = (Button) findViewById(R.id.leaderbords);
        startquiz.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivityquiz();

                    }
                });
        leaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizintent = new Intent(PrimeXHomeActivity.this,PrimeXLeaderboardsActivity.class);
                startActivity(quizintent);
            }
        });
    }

    private void savehighscoretofirebase() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference().child("game")
                .child("primex").child("score").child(user.getUid()).child("score");

        reference.setValue(highscore).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(PrimeXHomeActivity.this, "Done..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openActivityquiz() {
        Intent quizintent = new Intent(PrimeXHomeActivity.this,PrimeXQuesActivity.class);
        startActivity(quizintent);
    }
    @Override
    public void onBackPressed() {
        Intent quizintent = new Intent(PrimeXHomeActivity.this, GameActivity.class);
        startActivity(quizintent);
    }
    public void showhighscore() {
        SharedPreferences sharedPreferences = getSharedPreferences("highscore",MODE_PRIVATE);
        highscore = sharedPreferences.getInt("score",MODE_PRIVATE);
    }
}
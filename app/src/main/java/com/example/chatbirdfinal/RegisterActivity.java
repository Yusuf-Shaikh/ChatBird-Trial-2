package com.example.chatbirdfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username,fullname,email,password;
    Button register;
    TextView txt_login;

    FirebaseAuth auth;
    DatabaseReference reference,reference1;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        txt_login = (TextView)findViewById(R.id.txt_login);

        auth = FirebaseAuth.getInstance();

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname)
                        || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password))
                {
                    Toast.makeText(RegisterActivity.this, "All fields are required!!", Toast.LENGTH_SHORT).show();
                }
                else if(str_password.length() < 6)
                {
                    Toast.makeText(RegisterActivity.this, "Password must have atleast 6 charecter", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(str_username,str_fullname,str_email,str_password);
                }
            }
        });
    }

    private void register(final String username, final String fullname, String email, String password)
    {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            String userid = auth.getCurrentUser().getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object>hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username.toLowerCase());
                            hashMap.put("fullname",fullname);
                            hashMap.put("bio","");
                            hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/deltafinal-project.appspot.com/o/profile_image.png?alt=media&token=52e7f75b-9bac-4025-9663-03dee6069d23");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    reference1 = FirebaseDatabase.getInstance().getReference().child("game")
                                            .child("primex").child("score");
                                    HashMap<String, Object>hashMap1 = new HashMap<>();
                                    hashMap1.put("name",username.toLowerCase());
                                    hashMap1.put("image","https://firebasestorage.googleapis.com/v0/b/deltafinal-project.appspot.com/o/profile_image.png?alt=media&token=52e7f75b-9bac-4025-9663-03dee6069d23");
                                    hashMap1.put("score",0);
                                    reference1.child(userid).setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                pd.dismiss();
                                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        else
                        {
                            pd.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(RegisterActivity.this, "Error : "+message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

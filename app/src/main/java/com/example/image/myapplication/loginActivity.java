package com.example.image.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    private Button LoginButton;
    private EditText UserEmail,UserPassword;
    private TextView NeedNewAccountLink;
    private FirebaseAuth mAuth;
    private ProgressBar lodingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NeedNewAccountLink=findViewById(R.id.id_need_new_account);
        UserEmail=findViewById(R.id.id_logIn_email);
        UserPassword=findViewById(R.id.id_logIn_pass);
        LoginButton=findViewById(R.id.id_logIn_create_account);
        mAuth=FirebaseAuth.getInstance();
        lodingBar=new ProgressBar(this);


    }

    public void gotoRegister(View view) {
        Intent intent=new Intent(loginActivity.this,RagisterActivity.class);
        startActivity(intent);



    }


    @Override
    protected void onStart() {


        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser !=  null) {
            SendUserToMainActivity();
        }

    }

    public void loginButton(View view) {

        String email=UserEmail.getText().toString();
        String Pass=UserPassword.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please wtite your email...",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Pass))
        {
            Toast.makeText(this,"Please wtite your Password...",Toast.LENGTH_SHORT).show();
        }

        else
        {

            mAuth.signInWithEmailAndPassword(email,Pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                SendUserToMainActivity();


                                Toast.makeText(loginActivity.this,"You are Logged In Successfully...",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                String message=task.getException().getMessage();
                                Toast.makeText(loginActivity.this,"Error occured: "+ message,Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

        }


    }

    private void SendUserToMainActivity() {
        Intent intent=new Intent(loginActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();


    }
}

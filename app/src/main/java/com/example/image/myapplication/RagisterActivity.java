package com.example.image.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RagisterActivity extends AppCompatActivity {
    private ImageView icon;
    private EditText userEmail,userPass,userConfPass;
    private Button createAccountButton;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister);


        userConfPass=findViewById(R.id.id_reg_conf_pass);
        userEmail=findViewById(R.id.id_reg_email);
        userPass=findViewById(R.id.id_reg_pass);
        createAccountButton=findViewById(R.id.id_reg_create_account);
        mAuth = FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog(this);
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser !=  null) {
            SendUserToMainActivity();
        }
    }

    private void SendUserToMainActivity() {
        Intent intent=new Intent(RagisterActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();


    }



    public void button(View view) {
        String email=userEmail.getText().toString();
        String password=userPass.getText().toString();
        String confPass=userConfPass.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please write your email...",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please write your password...",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(confPass))
        {
            Toast.makeText(this,"Please write your confPass...",Toast.LENGTH_SHORT).show();
        }


        else if (!password.equals(confPass))
        {
            Toast.makeText(this,"password da not match with your confirm password",Toast.LENGTH_SHORT).show();
        }
        else
        {

            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait,while we are creatin your new Account....");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.dismiss();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                SendUserToSetUpActivity();
                                Toast.makeText(RagisterActivity.this,"you are authenticated successfully...",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String message=task.getException().getMessage();
                                Toast.makeText(RagisterActivity.this,"Error Occured :" +message,Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

        }



    }

    private void SendUserToSetUpActivity() {
        Intent setupIntent=new Intent(RagisterActivity.this,SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();

    }
}

package com.example.heypro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Remove FireBaseUser from Login Activity;

public class LoginActivity1 extends AppCompatActivity implements FirebaseAuth.AuthStateListener{

    private Button login ;
    ImageView phone;
    private FirebaseUser curr;
    private EditText email;
    private EditText passwd;
    private TextView needAcc;
    private ProgressDialog load;
    private TextView forgetAcc;

    private FirebaseAuth userCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);


        userCheck = FirebaseAuth.getInstance();



        initialize();

        needAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity1.this,RegisterActivity.class);
                startActivity(i);


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllowUsertofuture();

            }
        });


    }

    @Override
    protected void onStart() {
        FirebaseUser alreadyLoginOrNot = userCheck.getCurrentUser();
        if(alreadyLoginOrNot!= null){

            sendusertoMain();
        }

        super.onStart();
    }

    private void AllowUsertofuture() {
        String input1 = email.getText().toString();
        String input2 = passwd.getText().toString();
        if(TextUtils.isEmpty(input1)){
            Toast.makeText(this,"Kindly enter email plz....",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(input2)){
            Toast.makeText(this,"Kindly enter password plz....",Toast.LENGTH_LONG).show();
        }
        else{

            load.setTitle("Signing in..!");
            load.setMessage(" Being processed...");
            load.setCanceledOnTouchOutside(false);
            load.show();
            userCheck.signInWithEmailAndPassword(input1,input2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Signing in..",Toast.LENGTH_LONG).show();
                        sendusertoMain();
                        load.dismiss();

                    }
                    else{

                        String error  = task.getException().toString();
                        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                        load.dismiss();

                    }

                }
            });


        }


    }

    private void initialize() {
        login = findViewById(R.id.login_Button);

        email = findViewById(R.id.email);
        passwd = findViewById(R.id.passwd);
        needAcc  =findViewById(R.id.NeedNewAccount);
        forgetAcc = findViewById(R.id.forgetpasswd);
        load = new ProgressDialog(this);






    }
    private void sendusertoMain() {
        Intent i = new Intent(LoginActivity1.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();


    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

    }
}

package com.example.heypro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth u1;
    private ProgressDialog load;
    private EditText userR , passwdR , passwdRConfirm;
    private Button createAccount;
    DatabaseReference db;

    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        u1  = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        load = new ProgressDialog(this);
        userR = findViewById(R.id.emailR);
        passwdR = findViewById(R.id.passwdR);
        passwdRConfirm = findViewById(R.id.passwdConfirm);
        createAccount = findViewById(R.id.login_ButtonR);

//        createAccount.setEnabled(true);
        createAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                        CreateAccount();



                }
            });


    }

    @Override
    protected void onStart() {
        FirebaseUser alreadyLoginOrNot = u1.getCurrentUser();
        if(alreadyLoginOrNot!= null){

            sendUserToMainActivity();
        }

        super.onStart();
    }



        private void CreateAccount(){

        String input1 = userR.getText().toString();
        String input2 = passwdR.getText().toString();
        String input3 = passwdRConfirm.getText().toString();
        if(TextUtils.isEmpty(input1)){
            Toast.makeText(this,"Kindly enter email plz....",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(input2)){
            Toast.makeText(this,"Kindly enter password plz....",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(input3)){
            Toast.makeText(this,"Kindly Confirm password....",Toast.LENGTH_LONG).show();
        }

        else if(!input2.equals(input3)){
            Toast.makeText(this,"Password Mismatch....",Toast.LENGTH_LONG).show();
        }
        else{

            flag = 1;

            load.setTitle("Creating Account...!");
            load.setMessage("Plz wait until being processed...");
            load.setCanceledOnTouchOutside(false);
            load.show();
            u1.createUserWithEmailAndPassword(input1,input2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        String userUid = u1.getCurrentUser().getUid();
                        db.child("USERS").child(userUid).setValue("");

                        Toast.makeText(getApplicationContext(),"You are Authenticated ....",Toast.LENGTH_LONG).show();
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

    private void sendUserToSetupActivity() {
        Intent i =new Intent(RegisterActivity.this,SetupActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i);
        finish();
    }

    private void sendUserToMainActivity() {
        Intent i =new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }


}

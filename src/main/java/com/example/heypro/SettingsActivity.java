package com.example.heypro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mtool;
    private EditText username, fullname ,gender, relation , dob , country ,status;
    private Button bt;
    private CircleImageView usrImg;

    private FirebaseAuth check;
    private DatabaseReference db;
    String uid;
    private ProgressDialog load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        check = FirebaseAuth.getInstance();
        uid = check.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);


        load = new ProgressDialog(this);
        username = findViewById(R.id.profile_username);
        fullname = findViewById(R.id.profile_fullname);
        gender =findViewById(R.id.profile_gender);
        relation =findViewById(R.id.profile_relationship);
        dob = findViewById(R.id.profile_dob);
        country =findViewById(R.id.profile_country);
        status = findViewById(R.id.profile_status);
        bt  =findViewById(R.id.sv);
        usrImg = findViewById(R.id.Settings_profileImg);

        mtool = findViewById(R.id.Settings_toolbar);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String myImg = dataSnapshot.child("myself").getValue().toString();
                    Picasso.get().load(myImg).placeholder(R.drawable.profilelight).into(usrImg);
                    String status1 = dataSnapshot.child("Status").getValue().toString();
                    String r  = dataSnapshot.child("Relationship").getValue().toString();
                    String birth = dataSnapshot.child("DOB").getValue().toString();
                    String c = dataSnapshot.child("COUNTRY").getValue().toString();
                    String g = dataSnapshot.child("GENDER").getValue().toString();
                    String uname = dataSnapshot.child("USERNAME").getValue().toString();
                    String fname = dataSnapshot.child("FULLNAME").getValue().toString();
                    relation.setText(r);
                    status.setText(status1);
                    dob.setText(birth);
                    country.setText(c);
                    gender.setText(g);
                    username.setText(uname);
                    fullname.setText(fname);






                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateOriginalAccountInfo();
            }
        });

    }

    private void ValidateOriginalAccountInfo() {

        load.setTitle("Update...!");
        load.setMessage("Plz wait until your Info being updated.....");
        load.setCanceledOnTouchOutside(false);
        load.show();

        String uname1 = username.getText().toString();
        String fname1 = fullname.getText().toString();
        String c1 = country.getText().toString();
        String g1 = gender.getText().toString();
        String r1 = relation.getText().toString();
        String s1 = status.getText().toString();
        String dob1 = dob.getText().toString();

        if(TextUtils.isEmpty(uname1)){
            Toast.makeText(this, "Plz write username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(fname1)){
            Toast.makeText(this, "Plz write Profile_Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(c1)){
            Toast.makeText(this, "Plz write Country", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(g1)){
            Toast.makeText(this, "Plz write Gender", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(r1)){
            Toast.makeText(this, "Plz write Relationship ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(s1)){
            Toast.makeText(this, "Plz write Profile_Status", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(dob1)){
            Toast.makeText(this, "Plz write Date of Birth", Toast.LENGTH_SHORT).show();
        }

        else{
            updateInfo(uname1 , fname1 , c1 , g1 , r1 , dob1 , s1);

        }








    }

    private void updateInfo(String uname1, String fname1, String c1, String g1, String r1, String dob1, String s1) {

        HashMap umap = new HashMap();
        umap.put("USERNAME",uname1);
        umap.put("FULLNAME",fname1);
        umap.put("COUNTRY",c1);
        umap.put("GENDER",g1);
        umap.put("DOB",dob1);
        umap.put("Relationship",r1);
        umap.put("Status",s1);

        db.updateChildren(umap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if(task.isSuccessful()){
                    load.dismiss();

                    Toast.makeText(SettingsActivity.this, "Account Info updated", Toast.LENGTH_SHORT).show();
                    sendusertoMain();
                }
                else{
                    load.dismiss();
                    Toast.makeText(SettingsActivity.this, "Error occured.......!", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    private void sendusertoMain() {
        Intent i = new Intent(SettingsActivity.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();


    }


}

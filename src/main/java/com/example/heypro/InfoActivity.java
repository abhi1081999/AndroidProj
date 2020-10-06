package com.example.heypro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoActivity extends AppCompatActivity {



    private TextView username, fullname ,gender, relation , dob , country ,status;
    private CircleImageView usrImg;

    private FirebaseAuth check;
    private DatabaseReference db;
    private String getUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        username = findViewById(R.id.profile_name);
        fullname = findViewById(R.id.user_name);
        gender = findViewById(R.id.user_g);
        relation = findViewById(R.id.user_relation);
        dob = findViewById(R.id.user_dob);
        country = findViewById(R.id.user_country);
        status =  findViewById(R.id.user_status);
        usrImg = findViewById(R.id.myInfoImg);

        check = FirebaseAuth.getInstance();
        getUserId = check.getCurrentUser().getUid();

        db = FirebaseDatabase.getInstance().getReference().child("USERS").child(getUserId);

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
                    dob.setText("DOB:"+birth);
                    country.setText("Country:"+c);
                    gender.setText("Gender:"+g);
                    username.setText("@"+uname);
                    fullname.setText(fname);





                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(InfoActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
            }
        });





    }
}

package com.example.heypro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heypro.ViewHolder.FindFriendsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendsActivity extends AppCompatActivity {

    private RecyclerView searchResult;
    private EditText searchB;

    private Toolbar toolbar;

    FirebaseAuth check;
    String getid;
    DatabaseReference FriendsInDB;
    ArrayList<String> nameList;
    ArrayList<String> status;
    ArrayList<String> img;

    SearchListAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);


        nameList = new ArrayList<>();
        status = new ArrayList<>();
        img = new ArrayList<>();
        searchB = findViewById(R.id.search_box);


        toolbar = findViewById(R.id.find_fBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Your Crush");

        searchResult = findViewById(R.id.recyclerViewUsers);
        searchResult.setHasFixedSize(true);
        searchResult.setLayoutManager(new LinearLayoutManager(this));



        check = FirebaseAuth.getInstance();
        getid = check.getCurrentUser().getUid();
        FriendsInDB = FirebaseDatabase.getInstance().getReference();


//        searchBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String friend = searchB.getText().toString();
//
//                searchFriends(friend);
//
//
//            }
//        });

//        searchB.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                if(!s.toString().isEmpty()){
//
//                    callAdapter(s.toString());
//                }
//
//                else{
//                    nameList.clear();
//                    status.clear();
//                    img.clear();
//                    searchResult.removeAllViews();
//
//                }
//
//            }
//        });


    }

    private void callAdapter(final String searchS) {
//        FriendsInDB.child("USERS").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                int counter  =0;
//
//                    nameList.clear();
//                    status.clear();
//                    img.clear();
//                    searchResult.removeAllViews();
//
//                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
//
//                        String uid  = snap.getKey();
//                        String getName = snap.child("FULLNAME").getValue(String.class);
//                        String getStatus = snap.child("Status").getValue(String.class);
//                        String getpic = snap.child("myself").getValue(String.class);
//
//                        if (getName.toLowerCase().contains(searchS.toLowerCase())) {
//                            nameList.add(getName);
//                            status.add(getStatus);
//                            img.add(getpic);
//                            counter++;
//                        }
//                        else if(counter > 5){
//                            break;
//                        }
//
//                    }
//                    adapter = new SearchListAdapter(FindFriendsActivity.this, nameList, status, img);
//                    searchResult.setAdapter(adapter);
//
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }


}

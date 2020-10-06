package com.example.heypro;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.heypro.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth check;
    private DatabaseReference db , recyclerReference;
    private CircleImageView imageInUpperView;
    private TextView usernameInUpperView;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    ImageButton post1;
    String draw_open;
    String draw_close;
    private Toolbar toolbar;
    String userIDAttheEnd;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post, CategoryViewHolder>adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        post1  =findViewById(R.id.newPost);
        toolbar  = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");



        check = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference().child("USERS");
        userIDAttheEnd = check.getCurrentUser().getUid();
//        recyclerReference = FirebaseDatabase.getInstance().getReference().child("POSTS");


        navView = findViewById(R.id.navi_view);


//        final View upperView = navView.inflateHeaderView(R.layout.navigation_header);
//
//        imageInUpperView = upperView.findViewById(R.id.circle);
//        usernameInUpperView = upperView.findViewById(R.id.username);




        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recycler_view_all_posts);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);





        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                userMenuSelecter(menuItem);

                return false;
            }
        });




        post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToPostActivity();
            }
        });

//        displayAllUsersPost();

    }

    private void displayAllUsersPost() {

        options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(recyclerReference ,Post.class).build();


        adapter = new FirebaseRecyclerAdapter<Post, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(CategoryViewHolder holder, int position, Post model) {



                Picasso.get().load(model.getProfile_Image_User()).into(holder.c1);
                Picasso.get().load(model.getPost_Image()).into(holder.i1);

//                Toast.makeText(MainActivity.this,model.getPost_Image(), Toast.LENGTH_SHORT).show();
                holder.d1.setText(""+model.getDate());
                holder.u1.setText(model.getUserFullName());
                holder.des1.setText(model.getPost_Caption());
                holder.t1.setText(model.getTime());


            }
            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_posts_file,viewGroup,false);
                return new CategoryViewHolder(view);
            }
        };



        adapter.startListening();
        recyclerView.setAdapter(adapter);





    }

    private void sendUserToPostActivity() {
        Intent  i  = new Intent(MainActivity.this , PostActivity.class);

        startActivity(i);

    }

















    @Override
    protected void onStart() {


        FirebaseUser currfirebaseuser = check.getCurrentUser();
        if(currfirebaseuser == null){
            sendUserToLoginActivity();
        }
        else{

            checkUserExistenceInsideDatabase();
        }
        super.onStart();
    }

    private void checkUserExistenceInsideDatabase() {

        final String user_currently_online_id = check.getCurrentUser().getUid();


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChild(user_currently_online_id)){
                    sendUserToSetupActivity();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendUserToSetupActivity() {
        Intent i = new Intent(MainActivity.this,SetupActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void sendUserToLoginActivity() {
        Intent i = new Intent(MainActivity.this,LoginActivity1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);// user not allowed to go back on pressing back button

        startActivity(i);



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void userMenuSelecter(MenuItem m) {

        switch (m.getItemId()){

            case R.id.post: {
                Toast.makeText(this, "HI", Toast.LENGTH_LONG).show();
                break;

            }
            case R.id.logout:{
                check.signOut();
                sendUserToLoginActivity();
                break;
            }
            case R.id.completeProfileSetup:{
                sendUserToSetupActivity();
                break;
            }
            case R.id.Settings:
                sendUsertoSettingsActivity();
                break;
            case R.id.userInfo:
                sendUserToInfoActivity();
                break;
            case R.id.Find_Friends:
                sendUsertoSearchActivity();

        }
    }

    private void sendUsertoSearchActivity() {
        Intent i  =new Intent(MainActivity.this , FindFriendsActivity.class);
        startActivity(i);


    }

    private void sendUserToInfoActivity() {

        Intent i  =new Intent(MainActivity.this , InfoActivity.class);
        startActivity(i);



    }

    private void sendUsertoSettingsActivity() {

        Intent i=  new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(i);


    }


}

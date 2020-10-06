package com.example.heypro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class PostActivity extends AppCompatActivity {

    private ProgressDialog load;
    private Toolbar uPosttoolBar;
    private ImageButton addimg;
    private EditText des;
    private Button upload;
    private static final int galleryPic = 1;
    Uri imageUri;
    String description;

    DatabaseReference postDataRef , postRef;
    StorageReference uploadPostReference;
    FirebaseAuth check;
    String User_Uid;
    private ImageView imageView;

    private String saveCurrentDate , saveCurrentTime ,PostRandomName , downloadUrlFromStorage , down;

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imageView = findViewById(R.id.getimage);
        load = new ProgressDialog(this);
        upload = findViewById(R.id.upload);
        des = findViewById(R.id.caption);
        addimg = findViewById(R.id.addImage);
        uPosttoolBar = findViewById(R.id.update_post_toolBar);

        uploadPostReference = FirebaseStorage.getInstance().getReference();
        check = FirebaseAuth.getInstance();
        User_Uid = check.getCurrentUser().getUid();
        postDataRef = FirebaseDatabase.getInstance().getReference().child("USERS");
        postRef = FirebaseDatabase.getInstance().getReference().child("POSTS");
        user = check.getCurrentUser();




        setSupportActionBar(uPosttoolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("UpLoad Post");


        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = des.getText().toString();


                if(imageUri == null){
                    Toast.makeText(PostActivity.this, "Plz select the image", Toast.LENGTH_SHORT).show();
                }
                else{

                    load.setTitle("New Image Upload..!");
                    load.setMessage("Plz wait until your Image being uploaded...");
                    load.setCanceledOnTouchOutside(false);
                    load.show();
                    UploadToServer();

                }



            }
        });
    }

    private void UploadToServer() {

        Calendar CalDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(CalDate.getTime());


        Calendar CalTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(CalDate.getTime());

        PostRandomName = saveCurrentDate+" "+saveCurrentTime;




        final StorageReference storageReferencePathDesigned = uploadPostReference.child("My_Images").child(User_Uid).child(imageUri.getLastPathSegment()+" "+PostRandomName);

//        storageReferencePathDesigned.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//                downloadUrlFromStorage = task.getResult().getUploadSessionUri().toString();
//                Toast.makeText(PostActivity.this, downloadUrlFromStorage+"  hi", Toast.LENGTH_SHORT).show();
//                Picasso.get().load(downloadUrlFromStorage).placeholder(R.drawable.profilelight).into(imageView);
//                load.dismiss();
//            }
//        });

//        storageReferencePathDesigned.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                downloadUrlFromStorage = taskSnapshot.getUploadSessionUri().toString();
//
//                Toast.makeText(PostActivity.this, downloadUrlFromStorage+"  hi", Toast.LENGTH_SHORT).show();
//                Picasso.get().load(downloadUrlFromStorage).placeholder(R.drawable.profilelight).into(imageView);
//                load.dismiss();
//            }
//        });


//        storageReferencePathDesigned.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if(task.isSuccessful()){
//
//                    Task<Uri> u = task.getResult().getStorage().getDownloadUrl();
//                    downloadUrlFromStorage = task.getResult().getStorage().getDownloadUrl().toString();
//
//
//
//
//
//                    Toast.makeText(PostActivity.this, "Post Uploaded Publically. and Server Storage.!", Toast.LENGTH_SHORT).show();
//
//
//                    load.dismiss();
//                }
//
//
//            }
//        });



        UploadTask ut = storageReferencePathDesigned.putFile(imageUri);

        Task<Uri> hi = ut.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if(!task.isSuccessful()){

                }

                return storageReferencePathDesigned.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if(task.isSuccessful()){
                     down = task.getResult().toString();
                    Toast.makeText(PostActivity.this,down+" hii", Toast.LENGTH_SHORT).show();

//                    Picasso.get().load(down).placeholder(R.drawable.profilelight).into(imageView);
                    load.dismiss();
                    SavingPostInformationToDataBase();


                }

            }
        });







    }

    private void SavingPostInformationToDataBase() {
        postDataRef.child(User_Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String userfullname = dataSnapshot.child("FULLNAME").getValue().toString();
//                    String userprofileimage = dataSnapshot.child("Your_Image").getValue().toString();

                    HashMap PostMap = new HashMap();
                    PostMap.put("U_ID" , User_Uid);
                    PostMap.put("Date" , saveCurrentDate);
                    PostMap.put("Time" , saveCurrentTime);
                    PostMap.put("Post_Caption",description);
                    PostMap.put("Post_Image",down);
                    PostMap.put("Profile_Image_User" , user.getPhotoUrl().toString());
                    PostMap.put("UserFullName",userfullname);

                    postRef.child(User_Uid+" "+PostRandomName).updateChildren(PostMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if(task.isSuccessful()){
                                Toast.makeText(PostActivity.this," Success hi", Toast.LENGTH_SHORT).show();
                                SendUserToMainActivity();
                                load.dismiss();

                            }
                            else{
                                Toast.makeText(PostActivity.this,"Error Occured", Toast.LENGTH_SHORT).show();
                            load.dismiss();
                            }

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void openGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent ,galleryPic);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==galleryPic && data!=null && resultCode == RESULT_OK){



             imageUri = data.getData();
            addimg.setImageURI(imageUri);


        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SendUserToMainActivity();
        return super.onOptionsItemSelected(item);

    }








    private void SendUserToMainActivity() {

        Intent i = new Intent(PostActivity.this , MainActivity.class);
        startActivity(i);
        finish();
    }
}

package com.example.heypro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {


    private ProgressDialog load;
    private Button save;
    private EditText username;
    private EditText fullname;
    private EditText country;
    private CircleImageView img1;

    private FirebaseUser firebaseUser;
    Uri getFinalResult;


    final static int galleryPic = 1;


    FirebaseAuth check;
    DatabaseReference db;
    private StorageReference profile_image_storage;


    String heyID;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        load = new ProgressDialog(this);
        save = findViewById(R.id.save);
        username = (EditText) findViewById(R.id.usernamesetup);
        fullname = findViewById(R.id.fullnamesetup);
        country = findViewById(R.id.countrysetup);
        img1 =  findViewById(R.id.myImg);




        profile_image_storage = FirebaseStorage.getInstance().getReference();
        check  =FirebaseAuth.getInstance();
        heyID = check.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("USERS").child(heyID);

        firebaseUser = check.getCurrentUser();





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SaveAccountSetupInfo();
            }
        });


        if (firebaseUser != null) {

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){


                   String getMyself  = dataSnapshot.child("myself").getValue().toString();
                   Picasso.get().load(getMyself).placeholder(R.drawable.profilelight).into(img1);






                   load.dismiss();

               }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



//
//            String getMyself  = db.child("myself").toString();
//            Picasso.get().load(getMyself).placeholder(R.drawable.profilelight).into(img1);
//
//            Toast.makeText(this, getMyself+"  hii", Toast.LENGTH_LONG).show();
//
//
//            load.dismiss();
        }
        else{
            Toast.makeText(getApplicationContext(), "Your image is not being selected... ", Toast.LENGTH_SHORT).show();

        }


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,galleryPic);
            }
        });

//
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if(dataSnapshot.exists()){
//
//                    if(dataSnapshot.hasChild("directimage3")){
//
//                        String image11 = dataSnapshot.child("directimage3").getValue().toString();
//
//
//                        Picasso.with(SetupActivity.this).load(image11).placeholder(R.drawable.profilelight).into(img1);
//
//                    }
//                    else{
//
//                        Toast.makeText(SetupActivity.this, "Plz select image first......!", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == galleryPic && data!=null && resultCode == RESULT_OK){

//            Uri getImageUri = data.getData();
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);

        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult res = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){

                load.setTitle("Profile Image...!");
                load.setMessage("Plz wait until your Image being processed...");
                load.setCanceledOnTouchOutside(false);
                load.show();

//                Bitmap bitmap = (Bitmap) data.getExtras().get(String.valueOf(res));
//                img.setImageBitmap(bitmap);



                 getFinalResult = res.getUri();
                 selectImage();


//                final StorageReference filePath = profile_image_storage.child(heyID +".jpg");

//                filePath.putFile(getFinalResult).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        downloadImageUrl(filePath);
//
//                    }
//                });

//                filePath.putFile(getFinalResult).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//
//                        if(task.isSuccessful()){
//                            Toast.makeText(getApplicationContext(),"Image saved Successfully..to Storage !",Toast.LENGTH_LONG).show();
//
//
//
//                            final String downloadURL = task.getResult().getStorage().getDownloadUrl().toString();
//                            db.child("directimage3").setValue(downloadURL).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if(task.isSuccessful()){
//
//                                        Intent self = new Intent(SetupActivity.this,SetupActivity.class);
//                                        startActivity(self);
//                                        Toast.makeText(getApplicationContext(),"Image saved ..to FireBase DataBase!",Toast.LENGTH_LONG).show();
//                                        load.dismiss();
//
//
//                                    }
//                                    else{
//
//                                        Toast.makeText(getApplicationContext(),"error:"+ task.getException().toString(),Toast.LENGTH_LONG).show();
//                                        load.dismiss();
//
//
//
//                                    }
//
//
//
//                                }
//                            });
//
//                        }
//
//
//
//                    }
//                });
            }

            else{


                Toast.makeText(getApplicationContext(),"Image Not cropped",Toast.LENGTH_LONG).show();
                load.dismiss();


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private  void selectImage(){

        if(getFinalResult !=null){
            final StorageReference storageReference = profile_image_storage.child("My_Images").child(heyID).child(getFinalResult.getLastPathSegment());

//            storageReference.putFile(getFinalResult).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    String image_Url = taskSnapshot.getStorage().getDownloadUrl().toString();
//
//                    Toast.makeText(SetupActivity.this, image_Url, Toast.LENGTH_LONG).show();
//                    db.child("Confirm_Img").child(firebaseUser.getPhotoUrl().toString());
//
//
//
//
//
//
//                    if(firebaseUser.getPhotoUrl() != null || firebaseUser.getPhotoUrl() == null){
//                        downloadImageUrl(storageReference);
//                    }
//
//
////                    else if (firebaseUser.getPhotoUrl() != null) {
////
////
////                        Picasso.with(SetupActivity.this).load(firebaseUser.getPhotoUrl()).placeholder(R.drawable.profilelight).into(img1);
////
////
////                        load.dismiss();
////                    }
//                    else{
//                        Toast.makeText(getApplicationContext(), "Not ", Toast.LENGTH_SHORT).show();
//
//                    }
//
//
//
//
//                }
//            });


            UploadTask ut = storageReference.putFile(getFinalResult);
            Task<Uri> hi = ut.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if(!task.isSuccessful()){

                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful()){
                        String down = task.getResult().toString();
//                        Toast.makeText(SetupActivity.this, down + "  my Image", Toast.LENGTH_LONG).show();
                        load.dismiss();
                        Picasso.get().load(down).placeholder(R.drawable.profilelight).into(img1);

                        db.child("myself").setValue(down);

                    }

                }
            });


        }



    }
    private void downloadImageUrl(StorageReference filePath) {

        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                setUserProfileUri(uri);
            }
        });
    }

    private void setUserProfileUri(Uri uri) {
        final FirebaseUser firebaseUser = check.getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    if (firebaseUser.getPhotoUrl() != null) {


                        Picasso.get().load(firebaseUser.getPhotoUrl()).placeholder(R.drawable.profilelight).into(img1);




                        load.dismiss();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Not able to fulfill .....LUCK", Toast.LENGTH_SHORT).show();

                    }





                }
            }
        });


    }


    private void SaveAccountSetupInfo() {



        String input1 = username.getText().toString();
        String input2 = fullname.getText().toString();
        String input3 = country.getText().toString();

        if(TextUtils.isEmpty(input1)){
            Toast.makeText(this,"Kindly enter your Username",Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(input2)){
            Toast.makeText(this,"Kindly enter your Fullname",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(input3)){
            Toast.makeText(this,"Kindly enter your Country",Toast.LENGTH_LONG).show();
        }

        else {
            load.setTitle("...Setup is ongoing...!");
            load.setMessage("Plz wait until being processed...");
            load.setCanceledOnTouchOutside(false);
            load.show();
            HashMap map = new HashMap();
            map.put("USERNAME",input1);
            map.put("FULLNAME",input2);
            map.put("COUNTRY",input3);
            map.put("GENDER","none");
            map.put("DOB","none");
            map.put("Relationship","none");
            map.put("Status","LUV U ....Come in my life");
//            map.put("myself" , )

            db.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isSuccessful()){

                        sendUserToMainActivity();
                        Toast.makeText(getApplicationContext(),"ACCOUNT  Setup  DONE......",Toast.LENGTH_LONG).show();
                        load.dismiss();

                    }
                    else{
                        String error = task.getException().toString();

                        Toast.makeText(getApplicationContext(),"ERROR :" +error,Toast.LENGTH_LONG).show();
                        load.dismiss();
                    }
                }
            });

        }



    }



    private void sendUserToMainActivity() {

        Intent i =new Intent(SetupActivity.this , MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}

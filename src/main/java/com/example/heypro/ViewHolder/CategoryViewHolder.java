package com.example.heypro.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heypro.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView c1;
    public ImageView i1;
    public TextView d1,t1,u1 , des1;



    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        c1 = (CircleImageView) itemView.findViewById(R.id.post_profile_image11);
        i1 = (ImageView) itemView.findViewById(R.id.itsPost);
        d1 = itemView.findViewById(R.id.date1);
        t1 = itemView.findViewById(R.id.time1);
        u1 = itemView.findViewById(R.id.post_username);
        des1= itemView.findViewById(R.id.all_des);


    }
}

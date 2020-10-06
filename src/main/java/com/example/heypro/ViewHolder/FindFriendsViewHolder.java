package com.example.heypro.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.heypro.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendsViewHolder extends RecyclerView.ViewHolder {


    public CircleImageView c1;
    public TextView t1;
    public TextView s1;

    public FindFriendsViewHolder(@NonNull View itemView) {
        super(itemView);

        c1 = itemView.findViewById(R.id.allUsersProfileImage);
        t1 = itemView.findViewById(R.id.allUsersProfileName);
        s1 = itemView.findViewById(R.id.allUsersProfilestatus);


    }
}

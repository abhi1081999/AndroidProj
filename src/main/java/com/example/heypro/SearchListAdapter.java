package com.example.heypro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchViewHolder> {

    Context ctx;
    ArrayList<String> nameList;
    ArrayList<String> status;
    ArrayList<String> img;


    class SearchViewHolder extends RecyclerView.ViewHolder{


        public CircleImageView c1;
        public TextView t1;
        public TextView s1;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            c1 = itemView.findViewById(R.id.allUsersProfileImage);
            t1 = itemView.findViewById(R.id.allUsersProfileName);
            s1 = itemView.findViewById(R.id.allUsersProfilestatus);
        }
    }


    public SearchListAdapter(Context ctx, ArrayList<String> nameList ,ArrayList<String> status,
                                     ArrayList<String> img) {
        this.ctx = ctx;
        this.nameList = nameList;
        this.status = status;
        this.img = img;
    }

    @NonNull
    @Override
    public SearchListAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(ctx).inflate(R.layout.users_layout,viewGroup,false);
        return new SearchListAdapter.SearchViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder viewHolder, int i) {

        Picasso.get().load(img.get(i)).placeholder(R.drawable.profilelight).into(viewHolder.c1);
        viewHolder.t1.setText(nameList.get(i));
        viewHolder.s1.setText(status.get(i));



    }

    @Override
    public int getItemCount() {

        return nameList.size();
    }
}

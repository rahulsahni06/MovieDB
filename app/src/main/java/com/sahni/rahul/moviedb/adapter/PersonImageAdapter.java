package com.sahni.rahul.moviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.models.Images;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sahni on 14-Aug-17.
 */

public class PersonImageAdapter extends RecyclerView.Adapter<PersonImageAdapter.PersonImageViewHolder>{

    Context context;
    ArrayList<Images.Profiles> profilesArrayList;

    public PersonImageAdapter(Context context, ArrayList<Images.Profiles> profilesArrayList) {
        this.context = context;
        this.profilesArrayList = profilesArrayList;
    }

    @Override
    public PersonImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.person_image_layout, parent, false);

        return new PersonImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonImageViewHolder holder, int position) {

        Images.Profiles profiles = profilesArrayList.get(position);
        Picasso.with(context)
                .load(ApiClient.POSTER_BASE_URL + profiles.getFilePath())
                .placeholder(R.drawable.picasso_placeholder)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return profilesArrayList.size();
    }

    static class PersonImageViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public PersonImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.person_profile_image_view);
        }
    }
}

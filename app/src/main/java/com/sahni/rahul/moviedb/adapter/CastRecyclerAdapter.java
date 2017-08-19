package com.sahni.rahul.moviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.Person;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.interfaces.OnCastClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.sahni.rahul.moviedb.Networking.ApiClient.POSTER_BASE_URL;

/**
 * Created by sahni on 21-Jul-17.
 */

public class CastRecyclerAdapter extends RecyclerView.Adapter<CastRecyclerAdapter.CastViewHolder> {


    private ArrayList<Person> mArrayList;
    private Context mContext;
    OnCastClickListener listener;

    public CastRecyclerAdapter(ArrayList<Person> mArrayList, Context mContext, OnCastClickListener listener) {
        this.mArrayList = mArrayList;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cast_item_layout, parent, false);
        return new CastViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        Person cast = mArrayList.get(position);
        holder.castNameTextView.setText(cast.getName());
        holder.castCharacterTextView.setText(cast.getCharacterName());

        Picasso.with(mContext)
                .load(POSTER_BASE_URL+cast.getProfilePath())
                .placeholder(R.drawable.abstract_user)
                .into(holder.castImageView);

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class CastViewHolder extends RecyclerView.ViewHolder{

        ImageView castImageView;
        TextView castNameTextView;
        TextView castCharacterTextView;
        OnCastClickListener listener;

        public CastViewHolder(View itemView, final OnCastClickListener listener) {
            super(itemView);

            castImageView =(ImageView) itemView.findViewById(R.id.person_image_view);
            castNameTextView = (TextView) itemView.findViewById(R.id.person_name_text_view);
            castCharacterTextView = (TextView) itemView.findViewById(R.id.cast_character_text_view);
            this.listener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCastClicked(v,null);
                }
            });
        }
    }
}

package com.sahni.rahul.moviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.MovieDetailsResponse;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.interfaces.OnReviewClickListener;

import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;

/**
 * Created by sahni on 22-Jul-17.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewsViewHolder> {

    Context context;
    ArrayList<MovieDetailsResponse.ReviewsClass.ReviewClassResult> arrayList;
    OnReviewClickListener listener;


    public ReviewListAdapter(Context context, ArrayList<MovieDetailsResponse.ReviewsClass.ReviewClassResult> arrayList, OnReviewClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.review_item_layout, parent, false);
        return new ReviewsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        MovieDetailsResponse.ReviewsClass.ReviewClassResult review = arrayList.get(position);
        holder.userNameTextView.setText(review.getAuthor());
        holder.userReviewTextView.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder{

        ImageView userImageView;
        TextView userNameTextView;
        ExpandableTextView userReviewTextView;
        OnReviewClickListener listener;

        public ReviewsViewHolder(View itemView, final OnReviewClickListener listener) {
            super(itemView);

            userImageView = (ImageView) itemView.findViewById(R.id.user_image_view);
            userNameTextView = (TextView) itemView.findViewById(R.id.user_name_text_view);
            userReviewTextView = (ExpandableTextView) itemView.findViewById(R.id.user_review_text_view);
            this.listener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        ExpandableTextView textView = (ExpandableTextView) v.findViewById(R.id.user_review_text_view);
                        listener.onReviewClicked(textView);
                    }
                }
            });

        }
    }


}


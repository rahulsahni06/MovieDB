package com.sahni.rahul.moviedb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.squareup.picasso.Picasso;

import static com.sahni.rahul.moviedb.Networking.ApiClient.IMAGE_BASE_URL;


public class ImageSliderFragment extends Fragment {

//    ArrayList<MovieDetailsResponse.images.backdrops> mArrayList;
    private String imageUrl;
    private static final String TAG = "ImageSliderFragment";

    public ImageSliderFragment() {
        // Required empty public constructor
    }

    public static ImageSliderFragment newInstance(String imageUrl) {
        ImageSliderFragment fragment = new ImageSliderFragment();
        Bundle args = new Bundle();
        args.putString(IntentConstants.IMAGE_SLIDER_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(IntentConstants.IMAGE_SLIDER_URL);
            Log.i(TAG,"Image url = "+imageUrl);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_slider, container, false);



        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_slider_image_view);
//        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.image_slider_progress_bar);

        Log.i(TAG, "Displaying image: "+imageUrl);
        Picasso.with(getContext())
                .load(IMAGE_BASE_URL + imageUrl)
                .into(imageView);


    }
}

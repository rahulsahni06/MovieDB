package com.sahni.rahul.moviedb.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.Person;
import com.sahni.rahul.moviedb.Networking.PersonResponse;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.activities.PersonDetailsActivity;
import com.sahni.rahul.moviedb.adapter.SearchAdapter;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnPersonClickListener;
import com.sahni.rahul.moviedb.interfaces.UpdateSearchFragmentDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahni.rahul.moviedb.Networking.ApiClient.API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPersonFragment extends Fragment implements OnPersonClickListener, UpdateSearchFragmentDetails {


    private RecyclerView recyclerView;
    private SearchAdapter adapter;

    private ArrayList<Person> personArrayList;
    private ArrayList<Person> savedPersonArrayList;


    private TextView noPersonTextView;
    private String searchFor;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    private SearchView searchView;

    private CardView cardView;

    static final String TAG ="SearchPersonFragment";

    static final String PERSON_ARRAY_LIST = "person_array_list";


    public SearchPersonFragment() {
        // Required empty public constructor
    }


    public static SearchPersonFragment newInstance(){
        return new SearchPersonFragment();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(PERSON_ARRAY_LIST, personArrayList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
        String query = searchView.getQuery().toString();
        if(savedInstanceState != null){
            savedPersonArrayList = savedInstanceState.getParcelableArrayList(PERSON_ARRAY_LIST);
            if(savedPersonArrayList != null){
                Log.i(TAG, "onActivityCreated: saved array list is not null");
                if(personArrayList.isEmpty()){
                    Log.i(TAG, "onActivityCreated: person array list is empty");
                    displayDetails(savedPersonArrayList);
                }

            }
        }
        else{

            if(!query.isEmpty()){
                Log.i(TAG, "onViewCreated: calling update");
                update(query);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = (SearchView) getActivity().findViewById(R.id.search_view);

        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycler_view);
        noPersonTextView = (TextView) view.findViewById(R.id.search_status_text_view);
        noPersonTextView.setText("Search for persons");
        progressBar = (ProgressBar) view.findViewById(R.id.search_progress_bar);
        cardView = (CardView) view.findViewById(R.id.search_card_view);
        cardView.setVisibility(View.INVISIBLE);

        adapter = new SearchAdapter(getContext(), this);

        personArrayList = new ArrayList<>();


//        if(searchFor.equals(ContentUtils.SEARCH_TV_SHOW)){
//            moviesAdapter.setTvShowsArrayList(tvArrayList);
//        }
//        else{
//
//        }

        adapter.setPersonArrayList(personArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

//        String query = searchView.getQuery().toString();
//        if(!query.isEmpty() && personArrayList.isEmpty()){
//
//            Log.i(TAG, "onViewCreated: calling update");
//            update(query);
//        }


    }



    private void fetchDetails(String movieName) {



        ApiClient.getRetrofitClient()
                .getPersonSearchResult(API_KEY, "en-US", movieName)
                .enqueue(new Callback<PersonResponse>() {
                    @Override
                    public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {
                        if(response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            noPersonTextView.setVisibility(View.GONE);
                            displayDetails(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PersonResponse> call, Throwable t) {

                    }
                });


    }


    private void displayDetails(PersonResponse personResponse) {

        Log.i(TAG, "displayDetails: inside person response");

        if(personResponse.getPersonArrayList().isEmpty()){
            noPersonTextView.setText("Person not found");
            cardView.setVisibility(View.INVISIBLE);
            noPersonTextView.setVisibility(View.VISIBLE);

        }
        else{
            cardView.setVisibility(View.VISIBLE);
            personArrayList.addAll(personResponse.getPersonArrayList());
            adapter.notifyDataSetChanged();
        }


    }


    private void displayDetails(ArrayList<Person> arrayList) {

        Log.i(TAG, "displayDetails: inside array list signature method");

        if(arrayList.isEmpty() && !searchView.getQuery().toString().isEmpty()){
            noPersonTextView.setText("Person not found");
            cardView.setVisibility(View.INVISIBLE);
            noPersonTextView.setVisibility(View.VISIBLE);
        }
        else if(!arrayList.isEmpty()){
            noPersonTextView.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.VISIBLE);
            personArrayList.addAll(arrayList);
            adapter.notifyDataSetChanged();
        }


    }




    @Override
    public void onPersonClicked(View view) {

        int position = recyclerView.getChildAdapterPosition(view);
        Person person = personArrayList.get(position);
        Intent intent = new Intent(getActivity(), PersonDetailsActivity.class);
        intent.putExtra(IntentConstants.PERSON_KEY, person);
        startActivity(intent);

    }

    @Override
    public void update(String query) {
        personArrayList.clear();
        adapter.notifyDataSetChanged();
        cardView.setVisibility(View.INVISIBLE);
        noPersonTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        fetchDetails(query);
    }
}

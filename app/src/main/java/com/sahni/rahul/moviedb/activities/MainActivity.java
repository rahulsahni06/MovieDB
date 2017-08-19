package com.sahni.rahul.moviedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.adapter.MovieAndTvPagerAdapter;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.models.SessionId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahni.rahul.moviedb.Networking.ApiClient.API_KEY;
import static com.sahni.rahul.moviedb.Networking.ApiClient.AUTHENTICATION_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivityTag";
//    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout_main);


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_main);
        MovieAndTvPagerAdapter pagerAdapter = new MovieAndTvPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.movie_tv_view_pager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        searchView = (MaterialSearchView) findViewById(R.id.search_view);
//        searchView.setAdapter();
//        SearchView searchView;
//        searchView.setSu

//        if(checkIfUserIsLoggedIn(this)){
//            changeNavigationHeader();
//        }

    }



//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_in) {

            Intent intent = new Intent(this,AuthenticationActivity.class);
            startActivityForResult(intent, AUTHENTICATION_REQUEST_CODE);
            return true;
        }
        else if(id == R.id.action_search){
            startActivity(new Intent(this, SearchActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        final ProgressBar progressBar;
        final TextView textView;
        if(resultCode == RESULT_OK ){
            View view = LayoutInflater.from(this).inflate(R.layout.authentication_layout, null, false);
            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Authenticating")
                    .setView(view)
                    .setCancelable(false)
                    .create();
            dialog.show();

            if(requestCode == AUTHENTICATION_REQUEST_CODE){
                String accessToken = data.getStringExtra(IntentConstants.ACCESS_TOKEN_KEY);
                progressBar = (ProgressBar) view.findViewById(R.id.authentication_progress_bar);
                textView = (TextView) view.findViewById(R.id.authentication_text_view);
                ApiClient.getRetrofitClient()
                        .createSession(API_KEY, accessToken)
                        .enqueue(new Callback<SessionId>() {
                            @Override
                            public void onResponse(Call<SessionId> call, Response<SessionId> response) {
                                progressBar.setVisibility(View.GONE);
                                if(response.isSuccessful()){
                                    String sessionId = response.body().getSessionId();
                                    textView.setText(""+sessionId);
                                    Log.i(TAG, "session id = "+sessionId);
                                    dialog.setCanceledOnTouchOutside(true);

//                                    changeNavigationHeader();
                                }
                            }

                            @Override
                            public void onFailure(Call<SessionId> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });



            }
            else{

            }

        }
    }

//    private void changeNavigationHeader() {
//
//        ImageView navHeaderImageView = (ImageView) findViewById(R.id.nav_header_image_view);
//        TextView navHeaderNameTextView = (TextView) findViewById(R.id.nav_header_name_text_view);
//
//
//    }
}

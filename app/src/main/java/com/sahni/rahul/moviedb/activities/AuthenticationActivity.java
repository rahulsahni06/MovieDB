package com.sahni.rahul.moviedb.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.models.TokenRequest;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class AuthenticationActivity extends AppCompatActivity {

    private static final String TAG = "AuthenticationActivity";
    ProgressBar progressBar;
    public Map<String, String> map = new HashMap<>();
    WebView webView;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        webView = (WebView) findViewById(R.id.web_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final Button button = (Button) findViewById(R.id.token_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                ApiClient.getRetrofitClient()
                        .getToken(ApiClient.API_KEY)
                        .enqueue(new Callback<TokenRequest>() {
                            @Override
                            public void onResponse(Call<TokenRequest> call, Response<TokenRequest> response) {
                                if(response.isSuccessful()){
                                    button.setVisibility(GONE);
                                    progressBar.setVisibility(GONE);
                                    TokenRequest tokenRequest = response.body();
                                    if (tokenRequest != null) {

                                         token = tokenRequest.getToken();
                                        Log.i(TAG, "Token = "+token);
                                        Toast.makeText(AuthenticationActivity.this, "Token received", Toast.LENGTH_SHORT).show();
                                        startAuthentication(token);
                                    }
                                    else {
                                        Log.i(TAG,"Token is null");
                                    }

                                }
                                else{
                                    progressBar.setVisibility(GONE);
                                    Log.i(TAG, "not successful "+response.errorBody() );
                                }

                            }

                            @Override
                            public void onFailure(Call<TokenRequest> call, Throwable t) {
                                progressBar.setVisibility(GONE);
                                t.printStackTrace();

                            }
                        });
            }
        });
//        webView.setWebViewClient(new MyWebViewClient(progressBar));
//        webView.loadUrl();


    }

    private void startAuthentication(String token) {
        webView.setWebViewClient(new MyWebViewClient(progressBar));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.themoviedb.org/authenticate/"+token);

    }


    private class MyWebViewClient extends WebViewClient{


        ProgressBar progressBar;

        public MyWebViewClient(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }



        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.i(TAG,"Inside shouldOverrideUrlLoading");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            return super.shouldOverrideUrlLoading(view, request);

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                Log.i(TAG,"Inside shouldOverrideUrlLoading: getting url");
//                String url = request.getUrl().toString();
//                Log.i(TAG,"Url ="+url);
//                view.loadUrl(url);
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .baseUrl(url+"/")
//                        .build();
//                retrofit.create(ApiInterface.class)
//                        .authenticate()
//                        .enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, Response<Void> response) {
//                                if(response.isSuccessful()){
//                                    Log.i(TAG,"response successful ");
//                                    Log.i(TAG,"Header = "+ response.headers().names());
//                                    Set<String> set = response.headers().names();
//                                    for(String s : set){
//                                        Log.i(TAG,"Value ="+response.headers().get(s)+"---");
//                                    }
//
//                                }
//                                else{
//                                    Log.i(TAG,"response unsuccessful ");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                                t.printStackTrace();
//
//                            }
//                        });
//
////                for(String header : request.getRequestHeaders().keySet()) {
////                    Log.i(TAG, "before allow header data =" + map.get(header));
////                }
//
////                if(url.contains("allow")){
////
////                    Log.i(TAG,"Inside shouldOverrideUrlLoading: contains allow");
////                    Log.i(TAG,"Url ="+url);
////
////                    Retrofit retrofit = new Retrofit.Builder()
////                            .addConverterFactory(GsonConverterFactory.create())
////                            .baseUrl(url+"/")
////                            .build();
////                    retrofit.create(ApiInterface.class)
////                            .authenticate()
////                            .enqueue(new Callback<Void>() {
////                                @Override
////                                public void onResponse(Call<Void> call, Response<Void> response) {
////                                    if(response.isSuccessful()){
////                                        Log.i(TAG,"response successful ");
////                                        Log.i(TAG,"Header = "+ response.headers().names());
////                                        Set<String> set = response.headers().names();
////                                        for(String s : set){
////                                            Log.i(TAG,"Value ="+response.headers().get(s)+"---");
////                                        }
////
////                                    }
////                                    else{
////                                        Log.i(TAG,"response unsuccessful ");
////                                    }
////                                }
////
////                                @Override
////                                public void onFailure(Call<Void> call, Throwable t) {
////                                    t.printStackTrace();
////
////                                }
////                            });
////
//////                    for(String header : request.getRequestHeaders().keySet()) {
//////                        Log.i(TAG, "header data =" + map.get(header));
//////
//////
//////////                    map.putAll();
////////                    if(!map.isEmpty()){
////////                        for(String header : request.getRequestHeaders().keySet()){
////////                            Log.i(TAG,"header data ="+map.get(header) );
////////
////////                        }
////////                        String data = map.get("Authentication-Callback");
////////                        Log.i(TAG, "header data = "+data);
//////                    }
//////                    return true;
////                }
////                view.loadUrl(url);
//                Log.i(TAG,"Inside shouldOverrideUrlLoading: doesn't contain allow");
//                return true;



//            }
//
//            else{
//                Log.i(TAG,"Inside shouldOverrideUrlLoading: couldn't get url");
//                return true;
//            }



        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "onPageFinished : "+url);
            progressBar.setVisibility(GONE);
            if(url.contains("allow")){
                Intent intent = new Intent();
                intent.putExtra(IntentConstants.ACCESS_TOKEN_KEY, token);
                setResult(RESULT_OK, intent);
                finish();
            }
//            else if(url.contains("allow"))
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }


    }
}

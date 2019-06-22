package com.aamer.assigment.heady.atopstories;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.aamer.assigment.heady.atopstories.Adapters.StoryAdapter;
import com.aamer.assigment.heady.atopstories.Fragments.AlertDialogFragment;
import com.aamer.assigment.heady.atopstories.classes.MySingleton;
import com.aamer.assigment.heady.atopstories.classes.RecyclerItemClickListener;
import com.aamer.assigment.heady.atopstories.pojoclass.Stories;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Stories> itemsList = new ArrayList<>();
    private StoryAdapter storyListAdapter;
    LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    final String DOMAINURL="https://api.nytimes.com/svc/topstories/v2/automobiles.json?api-key=qsk2aLzuXjNSHG2842R49bpHUDUqSS0y";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        storyListAdapter = new StoryAdapter(getApplicationContext(), itemsList);


        RecyclerView recyclerViewStory =  findViewById(R.id.recyclerViewItems);
        progressBar = findViewById(R.id.progressBar);


        recyclerViewStory.setAdapter(storyListAdapter);
        recyclerViewStory.setLayoutManager(linearLayoutManager);

        recyclerViewStory.addOnItemTouchListener(
                new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this,StoryDetail.class);
                        intent.putExtra("articleURL",String.valueOf(itemsList.get(position).getArticleUrl()));
                        startActivity(intent);
                    }
                })
        );

        loadThisPageDataFromApi();
    }

    public void loadThisPageDataFromApi() {
        progressBar.setVisibility(View.VISIBLE);
        if(isNetworkAvailable())
        {
            StringRequest getUsersRequest = new StringRequest(Request.Method.GET, DOMAINURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressBar.setVisibility(View.GONE);
                    Log.i("Stories", response);
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        JSONObject jsonAdvertObject,jsonObjectImage;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonAdvertObject = jsonArray.getJSONObject(i);
                            Stories mStory = new Stories();
                            mStory.setTitle(jsonAdvertObject.getString("title"));
                            mStory.setDescription(jsonAdvertObject.getString("abstract"));
                            mStory.setArticleUrl(jsonAdvertObject.getString("short_url"));
                            JSONArray jsonArrayImage = jsonAdvertObject.getJSONArray("multimedia");
                            jsonObjectImage = jsonArrayImage.getJSONObject(0);
                            mStory.setUrl(jsonObjectImage.getString("url"));
                            itemsList.add(mStory);
                        }

                        if (itemsList.size() > 0) {
                            storyListAdapter.insertNewLoadedStory(itemsList);
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                        String msg = (e.getMessage() == null) ? "Failed!" : e.getMessage();
                        Log.d("JSONError", msg);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String msg = (error.getMessage() == null) ? "Failed!" : error.getMessage();
                    Log.d("VolleyFetchingError", msg);
                }
            }) ;
            MySingleton.getInstance(this).addToRequestQueue(getUsersRequest);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected())
        {
            isAvailable=true;
        }
        else{
            alertUserAboutError(getString(R.string.error_title),getString(R.string.network_unavailable));
        }
        return isAvailable;
    }

    private void alertUserAboutError(String title,String titleMessage) {

        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("error_title",title);
        bundle.putString("error_message",titleMessage);
        alertDialogFragment.setArguments(bundle);
        alertDialogFragment.show(getSupportFragmentManager(),"error_dialog");
    }

}

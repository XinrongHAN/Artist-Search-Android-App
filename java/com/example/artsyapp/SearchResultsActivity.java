package com.example.artsyapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity implements ArtistRecViewAdapter.RecyclerViewClickListener {

    private RecyclerView artistsRecView;
    private ArrayList<Artists> artists;
    private String query;
    private ProgressBar spinner;
    private TextView spinnertext;
    private ArtistRecViewAdapter.RecyclerViewClickListener listener;
    private String id;
    private String id_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ArtsyApp);
        setContentView(R.layout.activity_search_results);

        artistsRecView = findViewById(R.id.artistRecView);
        artists = new ArrayList<>(); //the information should come from API call

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            query = extras.getString("key");
        }
        setTitle(query);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinnertext = (TextView)findViewById(R.id.loading1);
        spinner.setVisibility(View.VISIBLE);
        spinnertext.setVisibility(View.VISIBLE);
        sendAndRequestResponse(query);
    }

    private void sendAndRequestResponse(String artistname) {
        final String url = "https://hxr571python78987.wl.r.appspot.com/artists?q="+artistname;

        //display the response on screen
        JsonObjectRequest myString = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                spinner.setVisibility(View.GONE);
                spinnertext.setVisibility(View.VISIBLE);
                JSONObject innerobject;
                JSONArray results = new JSONArray();
                int count = 0;
                try {
                    innerobject = response.getJSONObject("_embedded");
                    results = innerobject.getJSONArray("results");
                    count = response.getInt("total_count");
                } catch (JSONException e) {
                    Log.e("Error", "Response Error", e);
                }

                int numCards = Math.min(count, 10);

                if(numCards == 0){
                    CardView noresults = (CardView) findViewById(R.id.noresults);
                    noresults.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < numCards; i++) {
                    try {
                        JSONObject artist = results.getJSONObject(i);
                        String name = artist.getString("title");
                        String imgURL = artist.getJSONObject("_links").getJSONObject("thumbnail").getString("href");
                        String id = artist.getJSONObject("_links").getJSONObject("self").getString("href");
                        Artists artistobj = new Artists(name, imgURL,id);
                        artists.add(artistobj);
                    } catch (JSONException e) {
                        Log.e("Error", "Response Error", e);
                    }
                }
                System.out.println(artists.toString());
                ArtistRecViewAdapter adapter = new ArtistRecViewAdapter(SearchResultsActivity.this);
                adapter.setArtists(artists);
                System.out.println(adapter.getItemCount());
                artistsRecView.setAdapter(adapter);
                artistsRecView.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_LONG).show();
            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(myString);
    }

    @Override
    public void onClick(Intent intent) {
        String name = intent.getStringExtra("artistname");
        id = intent.getStringExtra("id");

        System.out.println(name);
        System.out.println(id);

        intent = new Intent(this,TabActivity.class);
        intent.putExtra("query",query);
        intent.putExtra("key",name);
        intent.putExtra("id",id);
        startActivity(intent);

        id_num = id.substring(34);
        System.out.println("id_num "+id_num);
    }

}
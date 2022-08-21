package com.example.artsyapp;

import static com.google.android.ump.FormError.ErrorCode.TIME_OUT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.window.SplashScreen;

import com.example.artsyapp.SectionedRecView.Favorite;
import com.example.artsyapp.SectionedRecView.FavoriteSection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements FavoriteSection.SectionOnClickListener{ //implements View.OnClickListener

    private String QueryArtists;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences sp_nation;
    private SharedPreferences sp_year;
    private SharedPreferences sp_id;
    private ArrayList<Favorite> allFavorite;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_ArtsyApp);
        setContentView(R.layout.activity_main);
        spinner = (ProgressBar)findViewById(R.id.progressBar0);
        spinner.setVisibility(View.VISIBLE);
        setTitle("ArtistSearch");

        setDate();
        setupHyperlink();

        sp_nation = getApplicationContext().getSharedPreferences("nationPrefs", Context.MODE_PRIVATE);
        sp_year = getApplicationContext().getSharedPreferences("yearPrefs",Context.MODE_PRIVATE);
        sp_id = getApplicationContext().getSharedPreferences("id",Context.MODE_PRIVATE);

        allFavorite = new ArrayList<>();
        getFavorites();


        //Set up favorite section
            // Create an instance of SectionedRecyclerViewAdapter
        sectionAdapter = new SectionedRecyclerViewAdapter();

            // Add your Sections
        FavoriteSection mysection = new FavoriteSection(MainActivity.this,this);
        mysection.setFavorites(allFavorite);
        sectionAdapter.addSection(mysection);

            // Set up your RecyclerView with the SectionedRecyclerViewAdapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mainrec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sectionAdapter);
        spinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(Intent intent) {
        String name = intent.getStringExtra("artistname");
        String id = intent.getStringExtra("id");

        System.out.println(name);
        System.out.println(id);

        intent = new Intent(this,TabActivity.class);
        intent.putExtra("key",name);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                QueryArtists = query;
                openSearchResults();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //get the existing favorites info from sharedPref and store them in arraylisit;
    private void getFavorites(){
        Map<String,String> allnations = (Map<String, String>) sp_nation.getAll();
        Map<String,String> allyears = (Map<String, String>) sp_year.getAll();
        Map<String,String> allids = (Map<String, String>) sp_id.getAll();

        for (Map.Entry<String,String> entry : allnations.entrySet()) {
            String name = entry.getKey();
            String nation = entry.getValue();
            String id = allids.get(name);
            String year = allyears.get(name);
            Favorite myFavorite = new Favorite(name,nation,year,id);
            allFavorite.add(myFavorite);
        }

        System.out.println(allFavorite);
    }

    private void setDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        TextView date = findViewById(R.id.date);
        date.setText(formattedDate);
        date.setTypeface(null, Typeface.BOLD);
    }

    private void setupHyperlink() {
        TextView link = findViewById(R.id.txtview);
        link.setTypeface(null, Typeface.BOLD_ITALIC);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setLinkTextColor(Color.parseColor("#FFACACAC"));
    }

    public void openSearchResults(){
        Intent intent = new Intent(this,SearchResultsActivity.class);
        intent.putExtra("key",QueryArtists);
        startActivity(intent);
    }

}
package com.example.artsyapp;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artsyapp.ui.main.SectionsPagerAdapter;
import com.example.artsyapp.databinding.ActivityTabBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TabActivity extends AppCompatActivity{

    private ActivityTabBinding binding;
    private ProgressBar spinner;
    private TextView spinnertext;
    private ConstraintLayout load;
    private ScrollView info;
    private String id;
    private String name;
    private String query;
    private String nation;
    private String birth;
    private String death;
    private String bio;
    private String id_num;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private ArtworkFragment artworkFragment;
    private int[] imglist = {R.drawable.ic_info_orange,R.drawable.ic_paint_orange};
    private SharedPreferences sp_nation;
    private SharedPreferences sp_year;
    private SharedPreferences addornot;
    private SharedPreferences sp_id;
    private String artistname;
    private ArtworkRecViewAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        tablayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        tablayout.setupWithViewPager(viewPager);


        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(new ArtistinfoFragment(),"  DETAILS");


        //get artistname and id from SearchResultsActivity intent
        artistname = "nothing";
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            artistname = extras.getString("key");
            id = extras.getString("id");
            query = extras.getString("query");
        }

        //set the title to be artistname
        setTitle(artistname);

        //show spinner
        load = (ConstraintLayout) findViewById(R.id.parentload);
        load.setVisibility(View.VISIBLE);


        //JSON response for DETAIL
        getArtistDetail(id);

        //pass data to artworkfragment and display
        artworkFragment = new ArtworkFragment();
        Bundle data = new Bundle();
        id_num = id.substring(34);
        data.putString("id",id_num);
        artworkFragment.setArguments(data);
        pagerAdapter.addFragment(artworkFragment,"  ARTWORKS");
        viewPager.setAdapter(pagerAdapter);

        //setup icons for tabs
        setTabIcons();

        //get shared preference
        sp_nation = getSharedPreferences("nationPrefs", Context.MODE_PRIVATE);
        sp_year = getSharedPreferences("yearPrefs",Context.MODE_PRIVATE);
        addornot = getSharedPreferences("addOrNot", Context.MODE_PRIVATE);
        sp_id = getSharedPreferences("id",Context.MODE_PRIVATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create the menu with star
        getMenuInflater().inflate(R.menu.addtofavorite,menu);
        MenuItem star = menu.findItem(R.id.favorite);

        //get the stored boolean value of this name
        Boolean initial = addornot.getBoolean(artistname + "?added", false);
        System.out.println("initialization " + initial);

        //if the name has already exists
        if (initial == true) {

            //change the empty star to filled star
            Drawable draw = getResources().getDrawable(R.drawable.ic_star_fill);
            star.setIcon(draw);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //if the back home arrow selected
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;

            // if the favorite toggler selected
        }else if(item.getItemId() == R.id.favorite){

            //initialize three editors
            SharedPreferences.Editor editor_nation = sp_nation.edit();
            SharedPreferences.Editor editor_year = sp_year.edit();
            SharedPreferences.Editor editorbool = addornot.edit();
            SharedPreferences.Editor editor_id = sp_id.edit();

            //check if this name has some information already
            //if not
            if(!sp_nation.contains(name)){
                editorbool.putBoolean(artistname+"?added",false).commit();
            }

            //get the boolean: if the name exits or not
            Boolean added = addornot.getBoolean(name+"?added",false);
            System.out.println("should be same as initialization "+added);

            if(!added){//if the name has not existed add it

                editor_nation.putString(name,nation).commit();
                editor_year.putString(name,birth).commit();
                editor_id.putString(name,id).commit();

                //toast message to notify name added
                Toast.makeText(getApplicationContext(), name+" is added to favorite!", Toast.LENGTH_LONG).show();

                //get the filled star icon and set
                Drawable draw = getResources().getDrawable(R.drawable.ic_star_fill);
                item.setIcon(draw);

                //toogle the boolean value
                editorbool.putBoolean(name+"?added",true).commit();
            }else{//if the name has already existed remove it

                editor_nation.remove(name).commit();
                editor_year.remove(name).commit();
                editor_id.remove(name).commit();

                //toast message to notify name removed
                Toast.makeText(getApplicationContext(), name+" is removed from favorite!", Toast.LENGTH_LONG).show();

                //get the empty star icon and set
                Drawable draw = getResources().getDrawable(R.drawable.ic_star);
                item.setIcon(draw);

                //toogle the boolean value
                editorbool.putBoolean(name+"?added",false).commit();
            }
            return true;
        }
        return false;
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = null;
        if(query == null){
            intent = new Intent(this,MainActivity.class);
        }else{
            intent = new Intent(this, SearchResultsActivity.class);
            intent.putExtra("key",query);
            intent.putExtra("id",id);
        }
        startActivity(intent);
        finish();
    }

    public void setTabIcons(){
        tablayout.getTabAt(0).setIcon(imglist[0]);
        tablayout.getTabAt(1).setIcon(imglist[1]);
    }

    private void getArtistDetail(String artistid) {
        final String url = "https://hxr571python78987.wl.r.appspot.com/info?id="+artistid;
        //display the response on screen
        JsonObjectRequest myString1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                load.setVisibility(View.GONE);
                info = (ScrollView)findViewById(R.id.info);
                info.setVisibility(View.VISIBLE);
                System.out.println(response);
                try {
                    name = response.getString("name");
                    nation = response.getString("nationality");
                    System.out.println(nation);
                    birth = response.getString("birthday");
                    System.out.println(birth);
                    death = response.getString("deathday");
                    System.out.println(death);
                    bio = response.getString("biography");
                    System.out.println(bio);
                    TextView nametxt = findViewById(R.id.namedetail);


                    if(nation.equals("")){
                        RelativeLayout nationrela = findViewById(R.id.relanation);
                        nationrela.setVisibility(View.GONE);
                    }else{
                        TextView nationtxt = findViewById(R.id.Nationalitydetail);
                        nationtxt.setText(nation);
                    }
                    if(birth.equals("")){
                        RelativeLayout birthrela = findViewById(R.id.relabirth);
                        birthrela.setVisibility(View.GONE);
                    }else{
                        TextView birthtxt = findViewById(R.id.Birthdaydetail);
                        birthtxt.setText(birth);
                    }
                    if(death.equals("")){
                        RelativeLayout deathrela = findViewById(R.id.reladeath);
                        deathrela.setVisibility(View.GONE);
                    }else{
                        TextView deathtxt = findViewById(R.id.Deathdaydetail);
                        deathtxt.setText(death);
                    }
                    if(bio.equals("")){
                        RelativeLayout biorela = findViewById(R.id.relabio);
                        biorela.setVisibility(View.GONE);
                    }else{
                        TextView biotxt = findViewById(R.id.biodetail);
                        biotxt.setText(bio);
                    }
                    nametxt.setText(name);


                } catch (JSONException e) {
                    Log.e("Error", "Response Error", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Response Error", error);
                Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_LONG).show();
            }
        });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(myString1);
    }

}
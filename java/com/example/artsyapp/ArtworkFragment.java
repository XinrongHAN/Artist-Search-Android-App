package com.example.artsyapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArtworkFragment extends Fragment implements ArtworkRecViewAdapter.RecyclerViewClickListener {

    private RecyclerView artworksRecView;
    private Context context;
    private Artworks artwork;
    private ArrayList<Artworks> artworks;
    private String id;
    private String artworkid;
    private Category cate;
    private int artworkcount;
    private Boolean isnull;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_artwork,container,false);
        artworksRecView = view.findViewById(R.id.artworkRecView);
        context = container.getContext();

        Bundle data = getArguments();
        if(data != null){
            id = data.getString("id");
        }else{
            System.out.println("NULL");
        }

        artworks = new ArrayList<>();
        getArtworks(id);


        return view;
    }

    @Override
    public void onClick(Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            artworkid = extras.getString("artworkid");
        }
        getCategory(artworkid);

    }

    private void getArtworks(String id) {
        final String url = "https://hxr571python78987.wl.r.appspot.com/artwork?id="+id;

        //display the response on screen
        JsonObjectRequest myString2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray artworkarray = response.getJSONObject("_embedded").getJSONArray("artworks");
                    artworkcount = artworkarray.length();
                    //show no results card
                    if(artworkcount == 0){
                        CardView noartwork = (CardView) ArtworkFragment.this.getView().findViewById(R.id.noartwork);
                        noartwork.setVisibility(View.VISIBLE);
                    }

                    for(int i = 0; i<artworkarray.length(); i++){
                        String artworkname = artworkarray.getJSONObject(i).getString("title");
                        String img = artworkarray.getJSONObject(i).getJSONObject("_links").getJSONObject("thumbnail").getString("href");
                        String year = artworkarray.getJSONObject(i).getString("date");
                        String artworkid =artworkarray.getJSONObject(i).getString("id");
                        Artworks artworkobject = new Artworks(artworkname,img,year,artworkid);
                        artworks.add(artworkobject);
                    }
                } catch (JSONException e) {
                    Log.e("Error", "Response Error", e);
                }
                System.out.println("when call this"+artworks);
                ArtworkRecViewAdapter adapter = new ArtworkRecViewAdapter(getContext(),ArtworkFragment.this);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                adapter.setArtworks(artworks);
                artworksRecView.setLayoutManager(manager);
                artworksRecView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Response Error", error);
                Toast.makeText(context, "ERROR!", Toast.LENGTH_LONG).show();
            }
        });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(myString2);
    }

    private void getCategory(String artistid) {
        final String url = "https://hxr571python78987.wl.r.appspot.com/genes?artwork_id="+artistid+"&size=1";
        //display the response on screen
        JsonObjectRequest myString1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray genearray = response.getJSONObject("_embedded").getJSONArray("genes");
                    if(genearray.length()==0){
                        isnull = true;
                    }else{
                        isnull = false;
                        JSONObject gene = genearray.getJSONObject(0);
                        String name = gene.getString("name");
                        String img = gene.getJSONObject("_links").getJSONObject("thumbnail").getString("href");
                        String des = gene.getString("description");
                        cate = new Category(name,img,des);
                    }
                } catch (JSONException e) {
                    Log.e("Error", "Response Error", e);
                }

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_card);
                TextView catename = (TextView) dialog.findViewById(R.id.catename);
                TextView catedes = (TextView) dialog.findViewById(R.id.descriptiontext);
                ImageView image = (ImageView) dialog.findViewById(R.id.cateimg);
                TextView category = (TextView) dialog.findViewById(R.id.Category);
                TextView discription = (TextView) dialog.findViewById(R.id.description);

                if(isnull){
                    CardView noartwork = (CardView) dialog.findViewById(R.id.nocategory);
                    noartwork.setVisibility(View.VISIBLE);
                    catename.setVisibility(View.GONE);
                    catedes.setVisibility(View.GONE);
                    image.setVisibility(View.GONE);
                    category.setVisibility(View.GONE);
                    discription.setVisibility(View.GONE);
                }else{
                    CardView noartwork = (CardView) dialog.findViewById(R.id.nocategory);
                    noartwork.setVisibility(View.GONE);
                    // set the custom dialog components - text, image
                    catename.setText(cate.getName());
                    catedes.setText(cate.getDes());
                    Picasso.with(context)
                            .load(cate.getImg())
                            .error(R.drawable.ic_logo_foreground)
                            .into(image);
                }
                dialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Response Error", error);
                Toast.makeText(getContext(), "ERROR!", Toast.LENGTH_LONG).show();
            }
        });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(myString1);
    }

}

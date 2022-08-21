package com.example.artsyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ArtworkRecViewAdapter extends RecyclerView.Adapter<ArtworkRecViewAdapter.ViewHolder> {
    private ArrayList<Artworks> artworks = new ArrayList<>();
    private Context context;
    private ArtworkRecViewAdapter.RecyclerViewClickListener listener;

    public ArtworkRecViewAdapter(Context context,final ArtworkRecViewAdapter.RecyclerViewClickListener listener){
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //the place to generate our view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_card_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.artworkName.setText(artworks.get(position).getName());
        holder.parent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //something to the next API call
                Intent intent = new Intent();
                intent.putExtra("artworkid",artworks.get(holder.getAdapterPosition()).getArtworkid());
                listener.onClick(intent);
            }
        });
        Picasso.with(context)
                .load(artworks.get(position).getImageUrl())
                .error(R.drawable.ic_logo_foreground)
                .resize(400,300)
                .centerCrop()
                .into(holder.imgURL);
    }

    @Override
    public int getItemCount() {
        return artworks.size();
    }

    public void setArtworks(ArrayList<Artworks> artworks) {
        this.artworks = artworks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ //INNERCLASS: holding the view items of the list of items
        private TextView artworkName;
        private CardView parent;
        private ImageView imgURL;


        public ViewHolder(final View itemView) {
            super(itemView);
            artworkName = itemView.findViewById(R.id.artworkName);
            parent = itemView.findViewById(R.id.parent);
            imgURL = itemView.findViewById(R.id.image);
        }
    }


    public interface RecyclerViewClickListener{
        void onClick(Intent intent);
    }
}

package com.example.artsyapp;

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

public class ArtistRecViewAdapter extends RecyclerView.Adapter<ArtistRecViewAdapter.ViewHolder> {
    private ArrayList<Artists> artists = new ArrayList<>();
    private Context context;
    private RecyclerViewClickListener listener;
    private String id;

    public ArtistRecViewAdapter(Context context){
        this.listener = ((RecyclerViewClickListener) context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //the place to generate our view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artists_card_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.artistName.setText(artists.get(position).getName());
        holder.parent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //something to the next API call
                Intent intent = new Intent();
                intent.putExtra("artistname",artists.get(holder.getAdapterPosition()).getName());
                intent.putExtra("id",artists.get(holder.getAdapterPosition()).getId());
                listener.onClick(intent);
            }
        });
        Picasso.with(context)
                .load(artists.get(position).getImageUrl())
                .resize(300,400)
                .error(R.drawable.ic_logo_foreground)
                .centerCrop()
                .into(holder.imgURL);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void setArtists(ArrayList<Artists> artists) {
        this.artists = artists;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ //INNERCLASS: holding the view items of the list of items
        private TextView artistName;
        private CardView parent;
        private ImageView imgURL;


        public ViewHolder(final View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.artistName);
            parent = itemView.findViewById(R.id.parent);
            imgURL = itemView.findViewById(R.id.image);
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(Intent intent);
    }
}

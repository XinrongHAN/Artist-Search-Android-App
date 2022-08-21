package com.example.artsyapp.SectionedRecView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.artsyapp.R;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class FavoriteSection extends Section {
    List<Favorite> favorites = new ArrayList<>();
    Context context;
    SectionOnClickListener listener;


    public FavoriteSection(Context context,final SectionOnClickListener listener) {

        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_fav_item)
                .headerResourceId(R.layout.section_fav_header)
                .build());

        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getContentItemsTotal() {
        return favorites.size(); // number of items of this section
    }

    public void setFavorites(ArrayList<Favorite> favorites) {
        this.favorites = favorites;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

        // bind your view here
        itemHolder.nameitem.setText(favorites.get(position).getName());
        itemHolder.nationitem.setText(favorites.get(position).getNation());
        itemHolder.yearitem.setText(favorites.get(position).getYear());
        itemHolder.redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("artistname",favorites.get(position).getName());
                intent.putExtra("id",favorites.get(position).getId());
                listener.onClick(intent);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        headerHolder.tvTitle.setText("FAVORITES");
    }

    public interface SectionOnClickListener{
        void onClick(Intent intent);
    }
}
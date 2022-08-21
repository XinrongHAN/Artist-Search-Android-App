package com.example.artsyapp.SectionedRecView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.artsyapp.R;

final class HeaderViewHolder extends RecyclerView.ViewHolder {

    final TextView tvTitle;

    HeaderViewHolder(@NonNull View view) {
        super(view);

        tvTitle = view.findViewById(R.id.tvTitle);
    }
}

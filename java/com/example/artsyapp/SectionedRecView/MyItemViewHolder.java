package com.example.artsyapp.SectionedRecView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artsyapp.R;

final class MyItemViewHolder extends RecyclerView.ViewHolder {

    final TextView yearitem;
    final TextView nationitem;
    final TextView nameitem;
    final ImageButton redirect;

    MyItemViewHolder(@NonNull View view) {
        super(view);

        nameitem = (TextView) itemView.findViewById(R.id.namefav);
        nationitem = (TextView) itemView.findViewById(R.id.nation);
        yearitem = (TextView) itemView.findViewById(R.id.birth);
        redirect = (ImageButton)itemView.findViewById(R.id.imageButton);

    }

}

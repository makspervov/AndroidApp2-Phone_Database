package com.example.phonedb;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhoneViewHolder extends RecyclerView.ViewHolder {
    private final TextView manufacturer;
    private final TextView model;

    public TextView getManufacturer() {
        return manufacturer;
    }

    public TextView getModel() {
        return model;
    }

    public PhoneViewHolder(@NonNull View itemView){
        super(itemView);
        this.manufacturer = itemView.findViewById(R.id.manufacturerTextView);
        this.model = itemView.findViewById(R.id.modelTextView);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) manufacturer.getLayoutParams();
        layoutParams.setMargins(16, 16, 0, 16);

        layoutParams = (ViewGroup.MarginLayoutParams) model.getLayoutParams();
        layoutParams.setMargins(500, 16, 0, 16);
    }
}

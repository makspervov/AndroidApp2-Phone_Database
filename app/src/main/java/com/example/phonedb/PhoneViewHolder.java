package com.example.phonedb;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhoneViewHolder extends RecyclerView.ViewHolder {
    private TextView manufacturer;
    private TextView model;

    public TextView getManufacturer() {
        return manufacturer;
    }

    public TextView getModel() {
        return model;
    }

    public PhoneViewHolder(@NonNull View itemView){
        super(itemView);
        this.manufacturer = itemView.findViewById(R.id.manufacturer_tv);
        this.model = itemView.findViewById(R.id.model_tv);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) manufacturer.getLayoutParams();
        layoutParams.setMargins(16, 16, 0, 16);

        layoutParams = (ViewGroup.MarginLayoutParams) model.getLayoutParams();
        layoutParams.setMargins(500, 16, 0, 16);
    }
}

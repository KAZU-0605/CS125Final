package com.example.cs125final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CountryAdapter extends ArrayAdapter<Countryelements> {
    public CountryAdapter(Context context, ArrayList<Countryelements> countryList) {
        super(context, 0, countryList);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return firstView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return firstView(position, convertView, parent);
    }
    private View firstView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.country_spinner_currency, parent, false
            );
        }
        ImageView imageViewCountry = convertView.findViewById(R.id.country_flag_image);
        TextView textViewCountry = convertView.findViewById(R.id.country_flag_name);
        Countryelements currentOne = getItem(position);
        if (convertView != null) {
            imageViewCountry.setImageResource(currentOne.getCountryImage());
            textViewCountry.setText(currentOne.getCountryName());
        }
        return convertView;
    }
}

package com.example.arun.internship;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Arun on 5/28/2018.
 */

public class BreachAccountListViewAdapter extends ArrayAdapter<BreachedAccount> {

    List<BreachedAccount> breachedAccounts;
    int resource;
    Context context;

    public BreachAccountListViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BreachedAccount> objects) {
        super(context, resource, objects);
        Log.d("Context",""+context);
        this.context = context;
        this.breachedAccounts = objects;
        this.resource = resource;


    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(resource, parent, false);

        }

        TextView titleView = (TextView) convertView.findViewById(R.id.textViewTitleResult);
        TextView dateView = (TextView) convertView.findViewById(R.id.textViewBreachedDateResult);
        TextView descriptionView = (TextView) convertView.findViewById(R.id.textViewDescriptionResult);
        TextView dataclassView = (TextView) convertView.findViewById(R.id.textViewDataClassesResult);

        titleView.setText(breachedAccounts.get(position).getTitle());
        dateView.setText(breachedAccounts.get(position).getBreachdate());
        descriptionView.setText(breachedAccounts.get(position).getDescription());
        dataclassView.setText(breachedAccounts.get(position).getDataclasses());
        return convertView;


    }

}


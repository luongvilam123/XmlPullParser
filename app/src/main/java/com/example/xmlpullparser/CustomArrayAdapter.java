package com.example.xmlpullparser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<RssItem> {
  Context context;
  int layoutresource;
  ArrayList<RssItem> arrayList;
    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<RssItem> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layoutresource=resource;
        this.arrayList=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View row=inflater.inflate(R.layout.layout_row_item,null);
        TextView titile =(TextView)row.findViewById(R.id.texttitle);
        TextView description=(TextView)row.findViewById(R.id.textdes);
        titile.setText(arrayList.get(position).getTitle());
        description.setText(arrayList.get(position).getDescription());
        return row;
    }
}
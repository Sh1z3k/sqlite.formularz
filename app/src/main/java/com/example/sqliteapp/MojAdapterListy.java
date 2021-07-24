package com.example.sqliteapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class MojAdapterListy extends CursorAdapter {

    public MojAdapterListy(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.wygladlisty, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nazwisko = view.findViewById(R.id.nazwisko);
        TextView imie = view.findViewById(R.id.imie);
        ImageView obrazek = view.findViewById(R.id.imageView4);
        String studentNazwisko = cursor.getString(cursor.getColumnIndex("NAZWISKO"));
        int image = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ZDJECIE")));
        String studentImie = cursor.getString(cursor.getColumnIndex("IMIE"));
        nazwisko.setText(studentNazwisko);
        imie.setText(studentImie);
        obrazek.setImageResource(image);
    }
}

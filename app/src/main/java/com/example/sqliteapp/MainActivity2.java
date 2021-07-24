package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;

import static com.example.sqliteapp.MainActivity.baza;
import static com.example.sqliteapp.MainActivity.db;

public class MainActivity2 extends AppCompatActivity {

    private MojAdapterListy mojAdapterListy;
    private TextView srednia, wiek;
    private ImageView zdjecie;
    private Button usunRekord;
    private Cursor cursor;
    String wydzial = "";
    boolean czyDobrzy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        ListView listView = findViewById(R.id.listV);

        srednia = findViewById(R.id.srednia);
        wiek = findViewById(R.id.wiek);
        zdjecie = findViewById(R.id.stZdjecie);
        usunRekord = findViewById(R.id.usunRekord);

        Intent intent = getIntent();
        if(intent != null) {
            wydzial = intent.getStringExtra("wydzial");
            czyDobrzy = intent.getBooleanExtra("czyDobrzy", false);
        }

        cursor = db.getStudentList(wydzial, czyDobrzy);
        if(cursor != null) {
            mojAdapterListy = new MojAdapterListy(getApplicationContext(), cursor, 0);
            listView.setAdapter(mojAdapterListy);
        }

        listView.setOnItemClickListener((adapterView, view1, position, id) -> {
            Cursor cursor1 = (Cursor) adapterView.getItemAtPosition(position);
            setTopFields(cursor1.getString(cursor.getColumnIndex("SREDNIA")),
                    cursor1.getString(cursor.getColumnIndex("DATAURODZENIA")),
                    Integer.parseInt(cursor1.getString(cursor.getColumnIndex("ZDJECIE")))
            );
            usunRekord.setOnClickListener(view -> {
                baza = db.getReadableDatabase();
                int rekordId = Integer.parseInt(cursor1.getString(cursor.getColumnIndex("_id")));
                db.usunRekord(rekordId, baza);
                Cursor currentCursor = db.getStudentList(wydzial, czyDobrzy);
                cursor = mojAdapterListy.swapCursor(currentCursor);
                mojAdapterListy.notifyDataSetChanged();
                setTopFields("", "", 0);
            });
        });
    }

    private void setTopFields(String srednia1, String wiek1, int zdjecie1) {
        srednia.setText(srednia1);
        wiek.setText(wiek1);
        zdjecie.setImageResource(zdjecie1);
    }
}
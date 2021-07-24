package com.example.sqliteapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private Spinner opcjeWyboru;
    private CheckBox chB;
    private String wybranyWydział;
    private boolean czyDobrzy;
    private TextView liczbaWieszy, noweZdjecie, noweNazwisko, noweImie, noweUrodzony, noweWydzial, noweSrednia;
    static Pomocnik db;
    static SQLiteDatabase baza;
    private EditText rekord;
    private ImageView pokazZdjecie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Pomocnik(this);
        
        opcjeWyboru = findViewById(R.id.spinner);
        pokazZdjecie = findViewById(R.id.pokazZdjecie);
        noweZdjecie = findViewById(R.id.noweZdjecie);
        noweNazwisko = findViewById(R.id.noweNazwisko);
        noweImie = findViewById(R.id.noweImie);
        noweUrodzony = findViewById(R.id.noweUrodzony);
        noweWydzial = findViewById(R.id.noweWydzial);
        noweSrednia = findViewById(R.id.noweSrednia);
        liczbaWieszy = findViewById(R.id.liczbaWieszy);
        chB = findViewById(R.id.checkBox);

        ArrayAdapter<CharSequence> adapterN = ArrayAdapter.createFromResource(this, R.array.wydziały, android.R.layout.simple_spinner_item);
        adapterN.setDropDownViewResource(android.R.layout.simple_spinner_item);

        opcjeWyboru.setAdapter(adapterN);
        opcjeWyboru.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wybranyWydział = adapterView.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        chB.setOnClickListener(view -> {
            if(chB.isChecked()) czyDobrzy = true;
            else czyDobrzy = false;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void zapisPoczątkowy(View x) {
        db.zmien(db.getWritableDatabase());
        baza = db.getWritableDatabase();
        Student st;
        Student.studenci.clear();
        Student.studenci.add(new Student(R.drawable.facet1,"Stanisław", "Antczak", "Mechaniczny", "1998-01-11", (float)4.13));
        Student.studenci.add(new Student(R.drawable.facet11,"Janusz", "Antkiewicz", "Mechaniczny", "1998-08-11", (float)4.19));
        Student.studenci.add(new Student(R.drawable.facet3,"Paweł", "Bartnik", "Nawigacyjny", "1998-01-11", (float)4.13));
        Student.studenci.add(new Student(R.drawable.facet4,"Adam", "Bartkowiak", "Elektryczny", "1996-07-11", (float)3.13));
        Student.studenci.add(new Student(R.drawable.facet5,"Paweł", "Zagórski", "Elektryczny", "1995-01-11", (float)3.99));
        Student.studenci.add(new Student(R.drawable.facet6,"Piotr", "Zawadzki", "PiT", "1997-03-21", (float)4.01));
        Student.studenci.add(new Student(R.drawable.facet7,"Bartosz", "Kowalski", "Nawigacyjny", "1995-11-11", (float)3.99));
        Student.studenci.add(new Student(R.drawable.facet7a,"Bartosz", "Janowski", "Elektryczny", "1995-07-11", (float)3.29));
        Student.studenci.add(new Student(R.drawable.facet8,"Mikołaj", "Żurawski", "PiT", "1997-12-21", (float)4.89));
        Student.studenci.add(new Student(R.drawable.facet9,"Paweł", "Filipiak", "PiT", "2001-04-29", (float)3.99));
        Student.studenci.add(new Student(R.drawable.facet10,"Zdzisław", "Gutkowski", "Nawigacyjny", "2001-05-29", (float)4.05));

        for (int i = 0; i < Student.studenci.size(); i++) {
            st = Student.studenci.get(i);
            db.dodajRekord(baza,
                    st.zdjęcie,
                    st.Nazwisko,
                    st.Imie,
                    st.Wydzial,
                    st.dataUrodzenia,
                    st.Srednia);
        }
        liczbaWieszy.setText("Zapisano " + Student.studenci.size() + " nowych studentów");
    }

    public void podajLiczbe(View w) {
        baza = db.getReadableDatabase();
        int x = db.podajLiczbeWierszy(baza);
        liczbaWieszy.setText("Liczba wierszy w tabeli: " + x);
    }

    public void pokazZdjecie(View w) {
        int zdjecie = getDrawableResource();
        if(zdjecie == 0) liczbaWieszy.setText("Nie ma takiego zdjęcia");
        pokazZdjecie.setImageResource(zdjecie);
    }

    public void pokazListe(View w) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("wydzial", wybranyWydział);
        intent.putExtra("czyDobrzy", czyDobrzy);
        startActivity(intent);
    }

    public void zapiszNowyRekord(View v) {
        if(fieldIsEmpty()) {
            liczbaWieszy.setText("Uzupełnij wszystkie pola przed dodaniem rekordu");
            return;
        }

        int zdjecie = getDrawableResource();
        if(zdjecie == 0) {
            liczbaWieszy.setText("Nie ma takiego zdjęcia");
            return;
        }

        String nazwisko = noweNazwisko.getText().toString();
        String imie = noweImie.getText().toString();
        String urodzony = noweUrodzony.getText().toString();
        String wydzial = noweWydzial.getText().toString();
        float srednia;
        try {
            srednia = Float.parseFloat(noweSrednia.getText().toString());
        } catch (NumberFormatException e) {
            liczbaWieszy.setText(e.getMessage());
            return;
        }

        baza = db.getReadableDatabase();
        db.dodajRekord(baza, zdjecie, nazwisko, imie, wydzial, urodzony, srednia);
        liczbaWieszy.setText("Zapisano nowy rekord");
    }

    private int getDrawableResource() {
        Context context = getApplicationContext();
        Resources resources = context.getResources();
        String nazwaZdjecia = noweZdjecie.getText().toString();
        return resources.getIdentifier(nazwaZdjecia, "drawable", context.getPackageName());
    }

    private boolean fieldIsEmpty() {
        if(noweNazwisko.getText().toString().trim().length() == 0) return true;
        if(noweImie.getText().toString().trim().length() == 0) return true;
        if(noweWydzial.getText().toString().trim().length() == 0) return true;
        if(noweSrednia.getText().toString().trim().length() == 0) return true;
        if(noweUrodzony.getText().toString().trim().length() == 0) return true;
        if(noweZdjecie.getText().toString().trim().length() == 0) return true;
        return false;
    }
}
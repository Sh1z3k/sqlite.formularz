package com.example.sqliteapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.annotation.Nullable;

public class Pomocnik extends SQLiteOpenHelper {

    private static final String DB_NAZWA = "bazaStudenci.db";
    private static final String TABELA_NAZWA = "studenci";
    private static final int DB_WERSJA = 1;

    public Pomocnik(@Nullable Context context) {
        super(context, DB_NAZWA, null, DB_WERSJA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABELA_NAZWA
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "ZDJECIE INTEGER, "
                + "NAZWISKO TEXT, "
                + "IMIE TEXT, "
                + "WYDZIAL TEXT, "
                + "DATAURODZENIA TEXT, "
                + "SREDNIA REAL);");
        // komunikat ="utworzona";
        Log.v("tworzenie","OK");
    }

    public boolean dodajRekord(SQLiteDatabase db,
                               int zd,
                               String nazw,
                               String imie,
                               String wydz,
                               String dU,
                               float sred) {
        ContentValues wartosci = new ContentValues();
        wartosci.put("ZDJECIE", zd);
        wartosci.put("NAZWISKO", nazw);
        wartosci.put("IMIE", imie);
        wartosci.put("WYDZIAL", wydz);
        wartosci.put("DATAURODZENIA", dU);
        wartosci.put("SREDNIA", sred);
        db.insert(TABELA_NAZWA,null, wartosci);
        return true;
    }

    public void zmien(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_NAZWA);
        db.execSQL("CREATE TABLE " + TABELA_NAZWA
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "ZDJECIE INTEGER, "
                + "NAZWISKO TEXT, "
                + "IMIE TEXT, "
                + "WYDZIAL TEXT, "
                + "DATAURODZENIA TEXT, "
                + "SREDNIA REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int podajLiczbeWierszy(SQLiteDatabase baza) {
        Cursor kursor = baza.rawQuery("Select COUNT(*) from " + TABELA_NAZWA, null);
        kursor.moveToNext();
        int x = kursor.getInt(0);
        return x;
    }

    public void usunRekord(int rekord, SQLiteDatabase baza) {
        baza.execSQL("DELETE FROM "+ TABELA_NAZWA + " WHERE _ID" + " = " + rekord + "");
        baza.close();
    }

    public Cursor getStudentList(String wydzial, boolean czyDobrzy) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        String[] args = new String[] {wydzial};

        if(czyDobrzy) {
            query = "SELECT _id, IMIE, NAZWISKO, ZDJECIE, SREDNIA, DATAURODZENIA FROM " + TABELA_NAZWA + " WHERE SREDNIA > 4  AND WYDZIAL = ?";
        } else {
            query = "SELECT _id, IMIE, NAZWISKO, ZDJECIE, SREDNIA, DATAURODZENIA FROM " + TABELA_NAZWA + "  WHERE WYDZIAL = ?";
        }

        if(wydzial.equals("Wszystkie")) {
            String wszystkie = query.split("  ")[0];
            query = wszystkie;
            args = null;
        }

        return db.rawQuery(query, args);
    }
}

package com.example.sqliteapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Student implements Comparable<Student>{

    int zdjęcie;
    String Nazwisko;
    String Imie;
    String Wydzial;
    String dataUrodzenia = "1999-09-14";
    float Srednia;

    public Student(int zdjęcie,
                   String Imie,
                   String Nazwisko,
                   String Wydzial,
                   String Ur,
                   float Srednia) {
        this.zdjęcie = zdjęcie;
        this.Nazwisko = Nazwisko;
        this.Imie = Imie;
        this.Wydzial = Wydzial;
        this.dataUrodzenia = Ur;
        this.Srednia = Srednia;
    }

    public Student() {}
    public static ArrayList<Student> studenci = new ArrayList<>();

    public String toString() {
        return this.Nazwisko + " " + this.Imie + "\nWydział: " + this.Wydzial;
    }

    static String wiek(String x) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd/");
        String data = sdf.format(new Date());
        final int bMiesiac = Integer.parseInt(data.substring(5, 7));
        final int bRok = Integer.parseInt(data.substring(0, 4));
        int uRok = Integer.parseInt(x.substring(0, 4));
        int uMiesiac = Integer.parseInt(x.substring(5, 7));
        int lata = bRok - uRok;
        int miesiace = bMiesiac - uMiesiac;
        if(miesiace < 0) {
            lata -= 1;
            miesiace = 12 + miesiace;
        }
        return lata + " lat " + miesiace + " miesięcy";
    }

    static LocalDate dzisiaj = LocalDate.now();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double wiekD(String data) {
        int uRok = Integer.parseInt(data.substring(0, 4));
        int uMiesiac = Integer.parseInt(data.substring(5, 7));
        int uDzień = Integer.parseInt(data.substring(8, 10));
        LocalDate dataUr = LocalDate.of(uRok, uMiesiac, uDzień);
        Period per = Period.between(dataUr, dzisiaj);
        return per.getYears() + per.getMonths() / 12.0 + per.getDays() / 365.0;
    }

    public boolean equals(Student inny) {
        return this.Nazwisko.equals(inny.Nazwisko)
                && this.Imie.equals(inny.Imie)
                && this.dataUrodzenia.equals(inny.dataUrodzenia);
    }

    @Override
    public int compareTo( Student student) {
        Collator c = Collator.getInstance(new Locale("pl", "PL"));
        int porównanieNazwisk = c.compare(this.Nazwisko,student.Nazwisko);
        int porównanieImion = c.compare(this.Imie,student.Imie);
        if(porównanieNazwisk == 0) return porównanieImion;
        return porównanieNazwisk;
    }
}
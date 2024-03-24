package com.example.tamagotchi

import Orzeszek
import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.ContentValues
import android.graphics.BitmapFactory
import android.provider.BaseColumns
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Gra : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gra)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val orzeszekView = Orzeszek(this, null)
        val parentLayout = findViewById<ConstraintLayout>(R.id.main) // lub inne odpowiednie id rodzica
        parentLayout.addView(orzeszekView)

        val mbd = BDManager(BDHelper(applicationContext))
        val czlowieczekImg = BitmapFactory.decodeResource(getResources(), R.drawable.czlowieczek1)

        mbd.zapiszWszystkieDane("Sliwka", czlowieczekImg)

        val imie = mbd.odczytajImie()
        val imieCzlowieczka = findViewById<TextView>(R.id.imieCzlowieczka)
        imieCzlowieczka.setText(imie);

        val img = mbd.odczytajObraz()
        val czlowieczekUIIMG = findViewById<ImageView>(R.id.czlowieczekUIIMG)
        czlowieczekUIIMG.setImageBitmap(img)

        /////////////////////////////////
        //1. baza danych istnieje/zawiera dane?
        //tak- Zczytaj z bazy
        //nie- przekieruj to activity StworzCzlowieczka (wybor wygladu, koloru, imienia)
            //stworzenie Czlowieczka
            //dodanie Czlowieczka do bazy
            //powrót/ponowne uruchomienie tej aktywności????
//////////////////////////////////////////////////////////////////////////////////

    }



}
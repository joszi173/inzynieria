package com.example.tamagotchi

import Orzeszek
import Orange
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
        val czlowieczek = Czlowieczek(this, "imie")



//tworzenie orzeszka/pomaranczy - narazie mozna jeden obiekt przesuwać
        val orangeView = Orange(this, null)
        val parentLayoutOrange = findViewById<ConstraintLayout>(R.id.main)
        parentLayoutOrange.addView(orangeView)








        val pasekGlod=PasekGlod()

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
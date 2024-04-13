package com.example.tamagotchi

import Orzeszek
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Gra : AppCompatActivity() {

    /*
    System.currentTimeMillis() - aktualny czas w milisekundach
     */

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

        //otworzenie/stworzenie bazy danych
        val mbd:BDManager = BDManager(BDHelper(applicationContext))



        //stworzenie człowieczka (klasa do wyrzucenia?)
        val czlowieczek = GameManager()

        //tworzenie orzeszka
        val orzeszekView = Orzeszek(this, null, czlowieczek)
        val parentLayout = findViewById<ConstraintLayout>(R.id.main) // lub inne odpowiednie id rodzica
        parentLayout.addView(orzeszekView)

        val glod=Glod(findViewById<ImageView>(R.id.pasekGlod), BitmapFactory.decodeResource(getResources(), R.drawable.pasek_full10), BitmapFactory.decodeResource(getResources(), R.drawable.pasek_1))

        //otwarcie obrazu człowieczka
        val czlowieczekImg = BitmapFactory.decodeResource(getResources(), R.drawable.czlowieczek1)


        if(!mbd.SprawdzCzyIstnieje()) {
            //aktualizowanie danych człowieczka w bazie
            mbd.zapiszWszystkieDane("Sliwka", czlowieczekImg)
        }
        //odczytanie imienia z bazy i wyświetlenie na ekran
        val imie = mbd.odczytajImie()
        val imieCzlowieczka = findViewById<TextView>(R.id.imieCzlowieczka)
        imieCzlowieczka.setText(imie.toString());

        //odczytanie obrazu z bazy i wyświetlenie na ekran
        val img = mbd.odczytajObraz()
        val czlowieczekUIIMG = findViewById<ImageView>(R.id.czlowieczekUIIMG)
        czlowieczekUIIMG.setImageBitmap(img)

    }



}
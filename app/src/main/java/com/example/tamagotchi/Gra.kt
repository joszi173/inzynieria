package com.example.tamagotchi

import Orzeszek
import Orange
import showNoti
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
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

        val glod=Glod(findViewById<ImageView>(R.id.pasekGlod), BitmapFactory.decodeResource(getResources(), R.drawable.pasek_full10), BitmapFactory.decodeResource(getResources(), R.drawable.pasek_1))

        //otworzenie/stworzenie bazy danych
        val mbd:BDManager = BDManager(BDHelper(applicationContext), this)

        ////////
        //tablica itemów, po dodaniu domku do przeniesienia do pokoju
        var listaItemow = arrayOf(20, 10, 13, 5)//zmienić na Itemy
        //index aktualnie wybranego itemu z tablicy
        var aktualnyItem = 2

        //stworzenie człowieczka (klasa do wyrzucenia?)
        //val czlowieczek = GameManager()


        
        //tworzenie orzeszka/pomaranczy - narazie mozna jeden obiekt przesuwać
        /*val orangeView = Orange(this, null)
        orangeView.ustaw(500f,1000f)
        val parentLayoutOrange = findViewById<ConstraintLayout>(R.id.main)
        parentLayoutOrange.addView(orangeView)
        */
        //powiadomienie
        showNoti(this, "Test tytuł", "Treść")



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

        //przycisk wołający interakcję (do przeniesienia do pokoju? wtedy można w każdym pokoju ustawić inną funkcję dla przycisku??)
        val przyciskKarmienia = findViewById<Button>(R.id.UzyjItemu)
        przyciskKarmienia.background=getResources().getDrawable(R.drawable.orange)
        przyciskKarmienia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                glod.zwiekszGlod(listaItemow[aktualnyItem])
                mbd.zapiszKarmienie()
                println("Karmienie " + listaItemow[aktualnyItem])
                println("Nowy czas karmienia " + mbd.odczytajOstatnieKarmienie())
                //tymczasowa zmiana indeksu do testu
                aktualnyItem++
                if (aktualnyItem >= 4) {
                    aktualnyItem = 0

                }
            }
        })

    }



}
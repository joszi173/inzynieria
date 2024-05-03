package com.example.tamagotchi

import Food
import showNoti
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.tamagotchi.db.czlowieczek
import kotlinx.coroutines.launch


class Gra : AppCompatActivity(), Glod.PasekGloduListener {

    /*
    System.currentTimeMillis() - aktualny czas w milisekundach
     */



    private lateinit var glod: Glod

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

        val dao = tamagotchiDatabase.getInstance(this).tamagotchiDao




        //Pasek glodu
        //val glod=Glod(100)
        // Inicjalizacja paska głodu
        glod = Glod(100)
        glod.setPasekGloduListener(this)






        //otworzenie/stworzenie bazy danych
       // val mbd:BDManager = BDManager(BDHelper(applicationContext), this)

        ////////
        //tablica itemów, po dodaniu domku do przeniesienia do pokoju
        var listaItemow = arrayOf(
            Food(this,null,BitmapFactory.decodeResource(getResources(), R.drawable.orange), 30),
            Food(this,null,BitmapFactory.decodeResource(getResources(), R.drawable.orzeszek), 10)
            )//zmienić na Itemy
        //index aktualnie wybranego itemu z tablicy
        var aktualnyItem = 1

        //stworzenie człowieczka (klasa do wyrzucenia?)
        //val czlowieczek = GameManager()


        
        //tworzenie orzeszka/pomaranczy - narazie mozna jeden obiekt przesuwać
        /*val orangeView = Orange(this, null)
        orangeView.ustaw(500f,1000f)
        val parentLayoutOrange = findViewById<ConstraintLayout>(R.id.main)
        parentLayoutOrange.addView(orangeView)
        */


        //powiadomienie jesli glod jest niski
        if(glod.jakGlodny()<=10)
            showNoti(this, "Tamagotchi jest glodny", "Nakarm mnie!")


        //otwarcie obrazu człowieczka
        val czlowieczekImg = BitmapFactory.decodeResource(getResources(), R.drawable.czlowieczek1)
        val czlowieczki = listOf( czlowieczek(1, "mandarynka", 12, System.currentTimeMillis(), czlowieczekImg))

        dao.insertAll(czlowieczki)

       // if(!mbd.SprawdzCzyIstnieje()) {
            //aktualizowanie danych człowieczka w bazie
        //    mbd.zapiszWszystkieDane("Sliwka", czlowieczekImg, 0)
       // }
        //odczytanie imienia z bazy i wyświetlenie na ekran
        //val imie = mbd.odczytajImie()
        //val imieCzlowieczka = findViewById<TextView>(R.id.imieCzlowieczka)
        //imieCzlowieczka.setText(imie.toString());

        //odczytanie obrazu z bazy i wyświetlenie na ekran
        //val img = mbd.odczytajObraz()
        //val czlowieczekUIIMG = findViewById<ImageView>(R.id.czlowieczekUIIMG)
        //czlowieczekUIIMG.setImageBitmap(img)

        //przycisk wołający interakcję (do przeniesienia do pokoju? wtedy można w każdym pokoju ustawić inną funkcję dla przycisku??)
        val przyciskKarmienia = findViewById<Button>(R.id.UzyjItemu)
        przyciskKarmienia.background=BitmapDrawable(getResources(), listaItemow[aktualnyItem].getBitmap())

        przyciskKarmienia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
               // glod.zwiekszGlod(listaItemow[aktualnyItem])
                listaItemow[aktualnyItem].onInteract(glod)
               // mbd.zapiszKarmienie()
                println("Karmienie " + listaItemow[aktualnyItem])
                println("Nowy czas karmienia " )//+ mbd.odczytajOstatnieKarmienie())
                //tymczasowa zmiana indeksu do testu
                aktualnyItem++
                if (aktualnyItem >= 2) {
                    aktualnyItem = 0

                }
                przyciskKarmienia.background=BitmapDrawable(getResources(), listaItemow[aktualnyItem].getBitmap())
            }
        })

    }

    override fun onPasekGloduChange(bitmapResource: Int) {
        runOnUiThread {
            // Tutaj ustaw obraz paska głodu na interfejsie użytkownika
            val pasekGloduImageView = findViewById<ImageView>(R.id.pasekGlod)
            pasekGloduImageView.setImageResource(bitmapResource)
        }
    }

}
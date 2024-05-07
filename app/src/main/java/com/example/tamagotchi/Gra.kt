package com.example.tamagotchi

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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tamagotchi.db.czlowieczek


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
        var listaItemow = listOf(
            //Food(this,null,BitmapFactory.decodeResource(getResources(), R.drawable.orange), 30),
            Food(1,13, 30,BitmapFactory.decodeResource(getResources(), R.drawable.orange), 3),
            //Food(this,null,BitmapFactory.decodeResource(getResources(), R.drawable.orzeszek), 10)
            Food(2,5,10,BitmapFactory.decodeResource(getResources(), R.drawable.orzeszek), 1)
            )//zmienić na Itemy
        dao.insertAll(listaItemow)
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



        showNoti(this, "Tamagotchi", "UgaBuga")
        //powiadomienie jesli glod jest niski
        //if(glod.jakGlodny()<=10)
//            showNoti(this, "Tamagotchi jest glodny", "Nakarm mnie!")


        //otwarcie obrazu człowieczka
        val czlowieczekImg = BitmapFactory.decodeResource(getResources(), R.drawable.czlow3)
        val czlowieczki = listOf( czlowieczek(1, "mandarynka", 12, System.currentTimeMillis(), czlowieczekImg))

        dao.insertAllCz(czlowieczki)

       // if(!mbd.SprawdzCzyIstnieje()) {
            //aktualizowanie danych człowieczka w bazie
        //    mbd.zapiszWszystkieDane("Sliwka", czlowieczekImg, 0)
       // }
        //odczytanie imienia z bazy i wyświetlenie na ekran
        //val imie = mbd.odczytajImie()
        val imie = dao.getAllCz().first().imie
        val imieCzlowieczka = findViewById<TextView>(R.id.imieCzlowieczka)
        imieCzlowieczka.setText(imie);
        println(imie)

        //odczytanie obrazu z bazy i wyświetlenie na ekran
        val img = dao.getAllCz().first().wyglad
        //val img = mbd.odczytajObraz()
        val czlowieczekUIIMG = findViewById<ImageView>(R.id.czlowieczekUIIMG)
        czlowieczekUIIMG.setImageBitmap(img)

        var listaJedzenia = dao.getAllGdzieWiecejNiz0().toMutableList()

        val przyciskKupowania= findViewById<Button>(R.id.kupItem)
        przyciskKupowania.background=BitmapDrawable(getResources(), listaJedzenia[0].bitmap)
        przyciskKupowania.text = listaJedzenia[0].koszt.toString()
        przyciskKupowania.setOnClickListener {
            if(dao.getAllCz().first().monety>=listaJedzenia[0].koszt) {
                dao.dodajIloscItem(1, listaJedzenia[0].id)
                dao.dodajMonety(-listaJedzenia[0].koszt)
                listaJedzenia = dao.getAllGdzieWiecejNiz0().toMutableList()
            }else{
                println("za malo monet")
            }
        }


        //przycisk wołający interakcję (do przeniesienia do pokoju? wtedy można w każdym pokoju ustawić inną funkcję dla przycisku??)
        val przyciskKarmienia = findViewById<Button>(R.id.UzyjItemu)
        przyciskKarmienia.background=BitmapDrawable(getResources(), listaJedzenia[aktualnyItem].bitmap)
        przyciskKarmienia.text = listaJedzenia[aktualnyItem].ilosc.toString()
        przyciskKarmienia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

               // glod.zwiekszGlod(listaItemow[aktualnyItem])
                listaItemow[aktualnyItem].onInteract(glod)
                println("Stary czas karmienia "+dao.getAllCz().first().czasOstatniegokarmienia)
                dao.updateCzasKarmienia(System.currentTimeMillis())
                println("Nowy czas karmienia "+dao.getAllCz().first().czasOstatniegokarmienia)
               // mbd.zapiszKarmienie()
                println("Karmienie " + listaItemow[aktualnyItem])
                println("Nowy czas karmienia " )//+ mbd.odczytajOstatnieKarmienie())

                listaJedzenia[aktualnyItem].ilosc--
                dao.insertAll(listaJedzenia.toList())
                if(listaJedzenia[aktualnyItem].ilosc<=0){
                    if(listaJedzenia.count()>0) {
                        listaJedzenia.removeAt(aktualnyItem)
                    }
                    if(listaJedzenia.count()<=0){
                        println("brak jedzenia")
                        przyciskKarmienia.text = "X"
                        przyciskKarmienia.background=BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.button))
                        przyciskKarmienia.isClickable=false
                        return
                    }
                }

                //tymczasowa zmiana indeksu do testu
                aktualnyItem++
                if (aktualnyItem >= listaJedzenia.count()) {
                    aktualnyItem = 0

                }
                przyciskKarmienia.text = listaJedzenia[aktualnyItem].ilosc.toString()
                przyciskKarmienia.background=BitmapDrawable(getResources(), listaJedzenia[aktualnyItem].bitmap)
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
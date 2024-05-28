package com.example.tamagotchi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tamagotchi.db.czlowieczek


class Gra : AppCompatActivity(), Potrzeba.PasekPotrzebyListener
{

    /*
    System.currentTimeMillis() - aktualny czas w milisekundach

    klasy itemów- na razie w Charach, miło by było to później zmienić na np. enumy, ale chary powinny dać radę
        'J'-jedzenie
        'Z'-zabawa
        'H'-higiena
        'S'-sen

     */



    private lateinit var glod: Potrzeba

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gra)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dao = tamagotchiDatabase.getInstance(this).tamagotchiDao

        var listaPokoi = listOf(
            //Food(this,null,BitmapFactory.decodeResource(getResources(), R.drawable.orange), 30),
            Pokoj(1,"kuchnia", 'J',BitmapFactory.decodeResource(getResources(), R.drawable.roomp)),
            Pokoj(2,"salon", 'Z',BitmapFactory.decodeResource(getResources(), R.drawable.shop))
            //Food(this,null,BitmapFactory.decodeResource(getResources(), R.drawable.orzeszek), 10)

        )//zmienić na Itemy
        dao.insertAllRooms(listaPokoi)



        //Pasek glodu
        //val glod=Glod(100)
        // Inicjalizacja paska głodu
        val glod = Potrzeba(this, 100)
        glod.setPasekPotrzebyListener(this)

        val domek = Domek(dao, glod, this, dao.getAllRooms())
        val sklepik = Sklepik(dao, this)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, domek)
                commit()
        }

        val przyciskDomek = findViewById<Button>(R.id.domekBtn)
        val przyciskSklepik = findViewById<Button>(R.id.sklepikBtn)





        //otworzenie/stworzenie bazy danych
       // val mbd:BDManager = BDManager(BDHelper(applicationContext), this)

        ////////
        //tablica itemów, po dodaniu domku do przeniesienia do pokoju
        var listaItemow = listOf(
            //Food(this,null,BitmapFactory.decodeResource(getResources(), R.drawable.orange), 30),
            Food(1,13, 30,BitmapFactory.decodeResource(getResources(), R.drawable.orange_),3),
            //Food(this,null,BitmapFactory.decodeResource(getResources(), R.drawable.orzeszek), 10)
            Food(2,5,10,BitmapFactory.decodeResource(getResources(), R.drawable.peanut_), 1)
            //Food(this,null,BitmapFactory.decodeResource(getResources(), R.drawable.orzeszek), 10)

            )//zmienić na Itemy
        dao.insertAllItems(listaItemow)
        //index aktualnie wybranego itemu z tablicy


        //stworzenie człowieczka (klasa do wyrzucenia?)
        //val czlowieczek = GameManager()


        
        //tworzenie orzeszka/pomaranczy - narazie mozna jeden obiekt przesuwać
        /*val orangeView = Orange(this, null)
        orangeView.ustaw(500f,1000f)
        val parentLayoutOrange = findViewById<ConstraintLayout>(R.id.main)
        parentLayoutOrange.addView(orangeView)
        */



        //otwarcie obrazu człowieczka
        val czlowieczki = listOf( czlowieczek(1, "mandarynka", 12, System.currentTimeMillis(), BitmapFactory.decodeResource(getResources(), R.drawable.czlow3)))

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






        /*
        val przyciskKupowania= findViewById<Button>(R.id.sklepikBtn)
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
        */


        /*val CzlowImageView = findViewById<ImageView>(R.id.czlowieczekImg)
        CzlowImageView.setOnClickListener()
        {
            czlowieczki[0].addMoney(50) // You can pass any desired amount here
            println("Monety: ")
            println(czlowieczki[0].monety)
            // Add money to the czlowieczek instance when the button is clicked

        }*/


        //przycisk wołający interakcję (do przeniesienia do pokoju? wtedy można w każdym pokoju ustawić inną funkcję dla przycisku??)

        przyciskDomek.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout, domek)
                    commit()
                }

            }
        })

        przyciskSklepik.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout, sklepik)
                    commit()
                }
            }
        })

        ZmienWyswietlaneMonety(dao.getAllCz().first().monety)




    }

    fun ZmienWyswietlaneMonety(monetki:Int){
        findViewById<TextView>(R.id.iloscMonet).setText(monetki.toString())
    }

    override fun onPasekPotrzebyChange(bitmapResource: Int) {
        runOnUiThread {
            // Tutaj ustaw obraz paska głodu na interfejsie użytkownika
            val pasekGloduImageView = findViewById<ImageView>(R.id.pasekGlod)
            pasekGloduImageView.setImageResource(bitmapResource)

        }
    }



}
package com.example.tamagotchi

import android.annotation.SuppressLint
import android.database.CursorWindow
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


enum class kolejnoscPotrzeb{
    GLOD, HIGIENA, SEN, ZABAWA
}
class Gra : AppCompatActivity(), Potrzeba.PasekPotrzebyListener
{

    /*
    System.currentTimeMillis() - aktualny czas w milisekundach

    klasy itemów- na razie w Charach, miło by było to później zmienić na np. enumy, ale chary powinny dać radę
        'J'-jedzenie
        'H'-higiena
        'S'-sen
        'Z'-zabawa

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


        //kolejność pokoi względem kolejności potrzeb w listach jest ważna!!!!
        try {
            val field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.setAccessible(true)
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val potrzeby = mutableListOf(Potrzeba(this), Potrzeba(this), Potrzeba(this), Potrzeba(this))
        potrzeby[kolejnoscPotrzeb.GLOD.ordinal].setPasekPotrzebyListener(this)
        potrzeby[kolejnoscPotrzeb.HIGIENA.ordinal].setPasekPotrzebyListener(this)
        potrzeby[kolejnoscPotrzeb.SEN.ordinal].setPasekPotrzebyListener(this)
        potrzeby[kolejnoscPotrzeb.ZABAWA.ordinal].setPasekPotrzebyListener(this)

        var listaPokoi = listOf(
            Kuchnia(1,"kuchnia", 'J',BitmapFactory.decodeResource(getResources(), R.drawable.kuchnia)),
            Lazienka(2,"lazienka", 'H',BitmapFactory.decodeResource(getResources(), R.drawable.lazienka)),
            Sypialnia(3,"sypialnia", 'S',BitmapFactory.decodeResource(getResources(), R.drawable.sypialnia)),
            Salon(4,"salon", 'Z',BitmapFactory.decodeResource(getResources(), R.drawable.roomp))
        )
        dao.insertAllRooms(listaPokoi)


        val pokoje= mutableListOf<Pokoj>();
        pokoje.add(dao.getKitchen())
        pokoje.add(dao.getBathRoom())
        pokoje.add(dao.getSleepinRoom())
        pokoje.add(dao.getLivinRoom())

        val domek = Domek(dao, potrzeby, this, pokoje)
        val sklepik = Sklepik(dao, this)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, domek)
                commit()
        }

        val przyciskDomek = findViewById<Button>(R.id.domekBtn)
        val przyciskSklepik = findViewById<Button>(R.id.sklepikBtn)

        ////////
        //tablica itemów, po dodaniu domku do przeniesienia do pokoju
        var listaItemow = listOf(
            Food(1,0, 30,BitmapFactory.decodeResource(getResources(), R.drawable.orang),15),
            Food(2,2,10,BitmapFactory.decodeResource(getResources(), R.drawable.peanut0), 4),
            Higene(3,1,1,BitmapFactory.decodeResource(getResources(), R.drawable.item_lazienka), 1),
            Fun(4,2,2,BitmapFactory.decodeResource(getResources(), R.drawable.item_salon), 1),
            Sleep(5,2,3,BitmapFactory.decodeResource(getResources(), R.drawable.item_sypialnia), 1),
            Food(6,5,2,BitmapFactory.decodeResource(getResources(), R.drawable.wisnia), 2),
            Food(7,0,25,BitmapFactory.decodeResource(getResources(), R.drawable.ser), 12),
            Food(8,0,50,BitmapFactory.decodeResource(getResources(), R.drawable.zupa), 30)
            )//zmienić na Itemy]]


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
        val czlowieczki = listOf( czlowieczek(1, "mandarynka", 12, System.currentTimeMillis(),System.currentTimeMillis(),System.currentTimeMillis(),System.currentTimeMillis(), BitmapFactory.decodeResource(getResources(), R.drawable.bnuy)))

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
        findViewById<TextView>(R.id.iloscMonetD).setText(monetki.toString())
    }

    override fun onPasekPotrzebyChange(bitmapResource: Int) {
        runOnUiThread {
            // Tutaj ustaw obraz paska głodu na interfejsie użytkownika
            val pasekGloduImageView = findViewById<ImageView>(R.id.pasekGlod)
            pasekGloduImageView.setImageResource(bitmapResource)

        }
    }



}
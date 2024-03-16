package com.example.tamagotchi

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.ContentValues
import android.provider.BaseColumns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Gra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gra)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /////////////////////////////////
        //1. baza danych istnieje/zawiera dane?
        //tak- Zczytaj z bazy
        //nie- przekieruj to activity StworzCzlowieczka (wybor wygladu, koloru, imienia)
            //stworzenie Czlowieczka
            //dodanie Czlowieczka do bazy
            //powrót/ponowne uruchomienie tej aktywności????
//////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////
        //osobna klasa na bazę, zapis i odczyt?
        //manager bazy danych
        val mbd = ManagerBazyDanych(applicationContext)
        //baza danych
        val bd:SQLiteDatabase = mbd.writableDatabase
        ////////////////////////////////////////////////
        val imie="Sliwka"
        val czlowieczek=Czlowieczek(this, imie)
        ///////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////
        //zapis do bazy
        val daneDoBazy=ContentValues().apply { put(InfoTabeli.KOLUMNA_IMIE, imie) }
        val nowyWiersz=bd.insert(InfoTabeli.NAZWA_TABELI, null, daneDoBazy)
//////////////////////////////////////////////////////////////////
        //czlowieczek.podajImie();
        ///////////////////////////////////////////////////////////////
        //odczyt z bazy
        val projection = arrayOf(InfoTabeli.KOLUMNA_IMIE)
        val selection = "${InfoTabeli.KOLUMNA_IMIE} = ?"
        val selectionArgs = arrayOf("Sliwka")
        val cursor = bd.query(InfoTabeli.NAZWA_TABELI,projection, selection, selectionArgs, null, null, null)
        cursor.moveToNext()
        val imieZBazy = cursor.getString(0)
        val imieCzlowieczka = findViewById<TextView>(R.id.imieCzlowieczka)
        imieCzlowieczka.setText(imieZBazy);
        ////////////////////////////////////////////////////////

    }



}
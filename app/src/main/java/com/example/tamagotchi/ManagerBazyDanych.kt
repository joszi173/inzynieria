package com.example.tamagotchi

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object InfoTabeli:BaseColumns{
    const val NAZWA_TABELI = "Tamagotchi"
    const val KOLUMNA_IMIE = "Imie"
}

object PodstawoweKomendy{
    const val SQL_STWORZ_TABELE = "CREATE TABLE ${InfoTabeli.NAZWA_TABELI} (${BaseColumns._ID} INTEGER PRIMARY KEY, ${InfoTabeli.KOLUMNA_IMIE} varchar(255) NOT NULL)"

    const val SQL_USUN_TABELE = "DROP TABLE IF EXISTS ${InfoTabeli.NAZWA_TABELI}"
}
class ManagerBazyDanych(context: Context):SQLiteOpenHelper(context, InfoTabeli.NAZWA_TABELI, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(PodstawoweKomendy.SQL_STWORZ_TABELE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(PodstawoweKomendy.SQL_USUN_TABELE)
        onCreate(db)
    }

}
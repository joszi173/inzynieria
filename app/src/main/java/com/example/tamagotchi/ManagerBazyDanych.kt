package com.example.tamagotchi

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.provider.BaseColumns
import java.io.ByteArrayOutputStream
/*
BDManager.apiszWszystkieDane(imie:String, obraz:Bitmap, monenty:Int) - wpisanie wszystkich danych do tablicy (część wewnątrz fcji, nie jest podawana jako argumenty)
BDManager.odczytajOstatnieKarmienie() - czas ostatnego karmienia w milisekundach
BDManager.odczytajMonety() - zwraca obecną liczbę monet
BDManager.odczytajImie() - zwraca obecną imię czlowieczka
BDManager.odczytajObraz() - zwraca obecną obraz z czlowieczkiem
BDManager.SprawdzCzyIstnieje() - sprawdzenie czy czlowieczek istnieje
BDManager.zapiszKarmienie() - zapisanie czasu karmienia
BDManager.zmienMonety(int):Boolean - dodanie monet do obecnej liczby, żeby odjąć przekazać ujemną wartość. Zwraca false jeśli liczba monet po zmianie byłaby ujemna
*/



object InfoTabeli:BaseColumns{
    const val NAZWA_TABELI = "Tamagotchi"
    const val KOLUMNA_OST_KARMIENIE = "OstatnieKarmienie"
    const val KOLUMNA_IMIE = "Imie"
    const val KOLUMNA_OBRAZ = "Obraz"
    const val KOLUMNA_MONETY = "Monety"
}

object PodstawoweKomendy{
    const val SQL_STWORZ_TABELE = "CREATE TABLE ${InfoTabeli.NAZWA_TABELI} (${BaseColumns._ID} INTEGER PRIMARY KEY, ${InfoTabeli.KOLUMNA_IMIE} VARCHAR(255),${InfoTabeli.KOLUMNA_OST_KARMIENIE} REAL, ${InfoTabeli.KOLUMNA_MONETY} INTEGER, ${InfoTabeli.KOLUMNA_OBRAZ} BLOB)"
    const val SQL_DODAJ_WIERSZ = "INSERT INTO ${InfoTabeli.NAZWA_TABELI} (${InfoTabeli.KOLUMNA_IMIE})VALUES( \"stworzonyWiersz\");"

    const val SQL_USUN_TABELE = "DROP TABLE IF EXISTS ${InfoTabeli.NAZWA_TABELI}"
}
class BDHelper(context: Context):SQLiteOpenHelper(context, InfoTabeli.NAZWA_TABELI, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(PodstawoweKomendy.SQL_STWORZ_TABELE)
        db?.execSQL(PodstawoweKomendy.SQL_DODAJ_WIERSZ)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(PodstawoweKomendy.SQL_USUN_TABELE)
        onCreate(db)
    }

}

class BDManager(val bdHelper: BDHelper, val context: Context){

    val bd:SQLiteDatabase=bdHelper.writableDatabase


     public fun onCreate(){

     }

    public fun stworzPustyWiersz(){//zamienić na Tworzenie Człowieczka
        val daneDoBazy= ContentValues().apply { put(InfoTabeli.KOLUMNA_IMIE, "nowyWiersz")}
        val nowyWiersz=bd.insert(InfoTabeli.NAZWA_TABELI, null, daneDoBazy)
    }

    public fun zapiszWszystkieDane(imie:String, img:Bitmap, mon:Int){

        val byteArray=ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
        val imgDoBazy = byteArray.toByteArray()

        val daneDoBazy= ContentValues().apply { put(InfoTabeli.KOLUMNA_IMIE, imie)
                                                put(InfoTabeli.KOLUMNA_OST_KARMIENIE, System.currentTimeMillis())
                                                put(InfoTabeli.KOLUMNA_OBRAZ, imgDoBazy)
                                                put(InfoTabeli.KOLUMNA_MONETY, mon)}
        val id=arrayOf("1")
        bd.update(InfoTabeli.NAZWA_TABELI, daneDoBazy, "_id = ?", id)
        //val nowyWiersz=bd.insert(InfoTabeli.NAZWA_TABELI, null, daneDoBazy)
        println(imie)
    }
    public fun zapiszKarmienie(){
        val curTime= ContentValues().apply { put(InfoTabeli.KOLUMNA_OST_KARMIENIE, System.currentTimeMillis())}
        val id=arrayOf("1")
        bd.update(InfoTabeli.NAZWA_TABELI, curTime, "_id = ?", id)
    }

    public fun zapiszImie(noweImie:String){
        val imie= ContentValues().apply { put(InfoTabeli.KOLUMNA_IMIE, noweImie)}
        val id=arrayOf("1")
        bd.update(InfoTabeli.NAZWA_TABELI, imie, "_id = ?", id)
    }
    public fun zapiszObraz(img:Bitmap){
        val byteArray=ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
        val imgDoBazy = byteArray.toByteArray()
        val obraz= ContentValues().apply { put(InfoTabeli.KOLUMNA_IMIE, imgDoBazy)}
        val id=arrayOf("1")
        bd.update(InfoTabeli.NAZWA_TABELI, obraz, "_id = ?", id)
    }

    public fun zapiszObrazZPliku(img:Int) {
        //R.drawable.czlowieczek1
        val czlowieczekImg = BitmapFactory.decodeResource(context.resources, img)
        zapiszObraz(czlowieczekImg)
    }

    public fun zmienMonety(monety:Int): Boolean {
        val obecneMonety = odczytajMonety()
        val noweMonety = obecneMonety?.plus(monety)
        if (noweMonety != null) {
            if(noweMonety<0){return false}
        val mon= ContentValues().apply { put(InfoTabeli.KOLUMNA_MONETY, noweMonety)}
        val id=arrayOf("1")
        bd.update(InfoTabeli.NAZWA_TABELI, mon, "_id = ?", id)
        return true
        }
        return false
    }


    @SuppressLint("Range")
    public fun odczytajOstatnieKarmienie(): Long {

        val projection = arrayOf(InfoTabeli.KOLUMNA_OST_KARMIENIE)
        val cursor:Cursor = bd.query(InfoTabeli.NAZWA_TABELI,projection, null, null/*selection, selectionArgs*/, null, null, null)
        cursor.moveToFirst()
        val czasKarmienia = cursor.getLong(cursor.getColumnIndex(projection[0]));
        cursor.close();
        return czasKarmienia

    }

    public fun SprawdzCzyIstnieje(): Boolean{

        val projection = arrayOf("*")
        val cursor:Cursor = bd.query(InfoTabeli.NAZWA_TABELI,projection, null, null/*selection, selectionArgs*/, null, null, null)
        cursor.moveToFirst()

        for(i in 1 .. cursor.columnCount-1){
            if(cursor.isNull(i)){
                println(cursor.getColumnName(i)+" NIE istnieje")
                return false
            }
            println(cursor.getColumnName(i)+" istnieje")

        }


        return true
    }


    @SuppressLint("Recycle", "Range")
    public fun odczytajImie(): String? {

            val projection = arrayOf(InfoTabeli.KOLUMNA_IMIE)
        val cursor:Cursor = bd.query(InfoTabeli.NAZWA_TABELI,projection, null, null/*selection, selectionArgs*/, null, null, null)
        cursor.moveToFirst()
        val imieZBazy = cursor.getString(cursor.getColumnIndex(projection[0]));
        cursor.close();
        println(imieZBazy)
        return imieZBazy
    }

    @SuppressLint("Recycle", "Range")
    public fun odczytajObraz(): Bitmap? {
        val projection = arrayOf(InfoTabeli.KOLUMNA_OBRAZ)
        val cursor:Cursor = bd.query(InfoTabeli.NAZWA_TABELI,projection, null, null/*selection, selectionArgs*/, null, null, null)
        cursor.moveToFirst()
        val bitmap = cursor.getBlob(cursor.getColumnIndex(projection[0]));
        val obraz = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
        cursor.close();
        return obraz
    }

    @SuppressLint("Recycle", "Range")
    public fun odczytajMonety(): Int? {
        val projection = arrayOf(InfoTabeli.KOLUMNA_MONETY)
        val cursor:Cursor = bd.query(InfoTabeli.NAZWA_TABELI,projection, null, null/*selection, selectionArgs*/, null, null, null)
        cursor.moveToFirst()
        val monety = cursor.getInt(cursor.getColumnIndex(projection[0]));
        cursor.close();
        return monety
    }

}


package com.example.tamagotchi

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.BaseColumns
import java.io.ByteArrayOutputStream

object InfoTabeli:BaseColumns{
    const val NAZWA_TABELI = "Tamagotchi"
    //const val KOLUMNA_ID = "ID"
    const val KOLUMNA_OST_KARMIENIE = "OstatnieKarmienie"
    const val KOLUMNA_IMIE = "Imie"
    const val KOLUMNA_OBRAZ = "Obraz"
}

object PodstawoweKomendy{
    const val SQL_STWORZ_TABELE = "CREATE TABLE ${InfoTabeli.NAZWA_TABELI} (${BaseColumns._ID} INTEGER PRIMARY KEY, ${InfoTabeli.KOLUMNA_IMIE} VARCHAR(255) NOT NULL,${InfoTabeli.KOLUMNA_OST_KARMIENIE} REAL, ${InfoTabeli.KOLUMNA_OBRAZ} BLOB NOT NULL)"

    const val SQL_USUN_TABELE = "DROP TABLE IF EXISTS ${InfoTabeli.NAZWA_TABELI}"
}
class BDHelper(context: Context):SQLiteOpenHelper(context, InfoTabeli.NAZWA_TABELI, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(PodstawoweKomendy.SQL_STWORZ_TABELE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(PodstawoweKomendy.SQL_USUN_TABELE)
        onCreate(db)
    }

}

class BDManager(val bdHelper: BDHelper){
    //al bdHelper: BDHelper=BDHelper(applicationContext)

    val bd:SQLiteDatabase=bdHelper.writableDatabase

    /*
    val bd:SQLiteDatabase = mbd.writableDatabase
    val daneDoBazy=ContentValues().apply { put(InfoTabeli.KOLUMNA_IMIE, imie) }
    val nowyWiersz=bd.insert(InfoTabeli.NAZWA_TABELI, null, daneDoBazy)
    val projection = arrayOf(InfoTabeli.KOLUMNA_IMIE)
    val selection = "${InfoTabeli.KOLUMNA_IMIE} = ?"
    val selectionArgs = arrayOf("Sliwka")
    val cursor = bd.query(InfoTabeli.NAZWA_TABELI,projection, selection, selectionArgs, null, null, null)
    cursor.moveToNext()
    val imieZBazy = cursor.getString(0)
    val imieCzlowieczka = findViewById<TextView>(R.id.imieCzlowieczka)
    imieCzlowieczka.setText(imieZBazy);
    */

     public fun onCreate(){

    }
    public fun zapiszWszystkieDane(imie:String, img:Bitmap){

        val byteArray=ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
        val imgDoBazy = byteArray.toByteArray()

        val daneDoBazy= ContentValues().apply { put(InfoTabeli.KOLUMNA_IMIE, imie)
                                                put(InfoTabeli.KOLUMNA_OST_KARMIENIE, System.currentTimeMillis())
                                                put(InfoTabeli.KOLUMNA_OBRAZ, imgDoBazy)}

        val nowyWiersz=bd.insert(InfoTabeli.NAZWA_TABELI, null, daneDoBazy)
        println(imie)
    }
    public fun zapiszKarmienie(){
        //System.currentTimeMillis();
        //mapOf<String, Long> = Map(InfoTabeli.KOLUMNA_OST_KARMIENIE, System.currentTimeMillis())
        val curTime= ContentValues().apply { put(InfoTabeli.KOLUMNA_OST_KARMIENIE, System.currentTimeMillis())}
        //val map = mapOf(InfoTabeli.KOLUMNA_OST_KARMIENIE to System.currentTimeMillis())
        val id=arrayOf("1")
        bd.update(InfoTabeli.NAZWA_TABELI, curTime, "_id = ?", id)
        //UPDATE table_name SET column1 = value1, column2 = value2 WHERE condition;
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



    @SuppressLint("Recycle", "Range")
    public fun odczytajImie(): String? {

            val projection = arrayOf(InfoTabeli.KOLUMNA_IMIE)
            //val selection = "${BaseColumns._ID} = ?"
        //val selectionArgs = arrayOf("0")
        val cursor:Cursor = bd.query(InfoTabeli.NAZWA_TABELI,projection, null, null/*selection, selectionArgs*/, null, null, null)
        //cursor.moveToNext()
        cursor.moveToFirst()
        val imieZBazy = cursor.getString(cursor.getColumnIndex(projection[0]));
        //if( cursor != null && cursor.moveToFirst() ){
        //    num = cursor.getString(cursor.getColumnIndex("ContactNumber"));
         //   cursor.close();
        //}
        //val imieZBazy = cursor.getString(0)
        cursor.close();
        println(imieZBazy)
        return imieZBazy
    }

    @SuppressLint("Recycle", "Range")
    public fun odczytajObraz(): Bitmap? {
        val projection = arrayOf(InfoTabeli.KOLUMNA_OBRAZ)
        //val selection = "${BaseColumns._ID} = ?"
        //val selectionArgs = arrayOf("0")
        val cursor:Cursor = bd.query(InfoTabeli.NAZWA_TABELI,projection, null, null/*selection, selectionArgs*/, null, null, null)
        //cursor.moveToNext()
        cursor.moveToFirst()
        val bitmap = cursor.getBlob(cursor.getColumnIndex(projection[0]));
        //if( cursor != null && cursor.moveToFirst() ){
        //    num = cursor.getString(cursor.getColumnIndex("ContactNumber"));
        //   cursor.close();
        //}
        //val imieZBazy = cursor.getString(0)
        val obraz = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
        cursor.close();
        return obraz
    }


}
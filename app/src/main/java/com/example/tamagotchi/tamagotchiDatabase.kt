package com.example.tamagotchi

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.tamagotchi.db.czlowieczek
import com.example.tamagotchi.db.tamagotchiDao
import java.io.ByteArrayOutputStream

@Database(
    entities = [
        czlowieczek::class
    ],
    version = 1 ,

)
@TypeConverters(Converters::class)
abstract class tamagotchiDatabase:RoomDatabase(){
    //abstract val tamagotchidaoImpl
    abstract val tamagotchiDao:tamagotchiDao

    companion object {

        @Volatile
        private var INSTANCE: tamagotchiDatabase? = null

        fun getInstance(context:Context):tamagotchiDatabase{
            synchronized(this){
                return INSTANCE?: Room.databaseBuilder(context.applicationContext, tamagotchiDatabase::class.java, "tamaDB").allowMainThreadQueries().build().also{ INSTANCE=it}
            }
        }


    }

}


class Converters{
    @TypeConverter
    fun BitmapToByteArray(bmp: Bitmap): ByteArray? {
        val byteArray= ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
        return byteArray.toByteArray()
    }

    @TypeConverter
    fun ByteArrayToBitmap(btarr: ByteArray): Bitmap? {
            return BitmapFactory.decodeByteArray(btarr, 0, btarr.size)
    }

}
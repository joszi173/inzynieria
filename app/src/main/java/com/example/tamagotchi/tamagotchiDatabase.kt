package com.example.tamagotchi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tamagotchi.db.czlowieczek
import com.example.tamagotchi.db.tamagotchiDao

@Database(
    entities = [
        czlowieczek::class
    ],
    version = 1
)
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
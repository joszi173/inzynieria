package com.example.tamagotchi.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface tamagotchiDao {

    @Query("SELECT * FROM czlowieczek")
    fun getAll(): List<czlowieczek>

    @Query("UPDATE czlowieczek SET czasOstatniegokarmienia = :czasKarmienia WHERE id = 1")
    fun update(czasKarmienia: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(czlowieczek: List<czlowieczek>)

    @Delete
    fun delete(czlowieczek: czlowieczek)

}




package com.example.tamagotchi.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tamagotchi.Food
import com.example.tamagotchi.Item


@Dao
interface tamagotchiDao {

    @Query("SELECT * FROM czlowieczek")
    fun getAllCz(): List<czlowieczek>

    @Query("UPDATE czlowieczek SET czasOstatniegokarmienia = :czasKarmienia WHERE id = 1")
    fun updateCz(czasKarmienia: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCz(czlowieczek: List<czlowieczek>)

    @Delete
    fun deleteCz(czlowieczek: czlowieczek)

    @Query("SELECT * FROM item")
    fun getAll(): List<Food>

    @Query("SELECT * FROM item WHERE ilosc>0")
    fun getAllGdzieWiecejNiz0(): List<Food>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(itemy: List<Item>)

    @Delete
    fun delete(item: Item)

}




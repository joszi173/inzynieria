package com.example.tamagotchi.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tamagotchi.Food
import com.example.tamagotchi.Fun
import com.example.tamagotchi.Higene
import com.example.tamagotchi.Item
import com.example.tamagotchi.Kuchnia
import com.example.tamagotchi.Lazienka
import com.example.tamagotchi.Pokoj
import com.example.tamagotchi.Salon
import com.example.tamagotchi.Sleep
import com.example.tamagotchi.Sypialnia


@Dao
interface tamagotchiDao {

    @Query("SELECT * FROM czlowieczek")
    fun getAllCz(): List<czlowieczek>

    @Query("UPDATE czlowieczek SET czasOstatniegokarmienia = :czasKarmienia WHERE id = 1")
    fun updateCzasKarmienia(czasKarmienia: Long)

    @Query("UPDATE czlowieczek SET czasOstatniegomycia = :czasMycia WHERE id = 1")
    fun updateCzasMycia(czasMycia: Long)

    @Query("UPDATE czlowieczek SET czasOstatniegospania = :czasSpania WHERE id = 1")
    fun updateCzasSpania(czasSpania: Long)

    @Query("UPDATE czlowieczek SET czasOstatniejzabawy = :czasZabawy WHERE id = 1")
    fun updateCzasZabawy(czasZabawy: Long)


    @Query("UPDATE czlowieczek SET monety = monety+:roznicaMonet WHERE id = 1")
    fun dodajMonety(roznicaMonet: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCz(czlowieczek: List<czlowieczek>)

    @Delete
    fun deleteCz(czlowieczek: czlowieczek)

    @Query("SELECT * FROM item WHERE klasa=74")
    fun getAllFood(): List<Food>

    //J-74, S-83, H-72, Z-90

    @Query("SELECT * FROM item WHERE ilosc>0 AND klasa=:itemClass")//przekonwertować char na int jeśli nie działa
    fun getAllRoomItemMoreThan0(itemClass:Char): List<Item>


///////
    @Query("SELECT * FROM item WHERE ilosc>0 AND klasa=74")
    fun getAllFoodMoreThan0(): List<Food>

    @Query("SELECT * FROM item WHERE ilosc>0 AND klasa=72")
    fun getAllHigeneMoreThan0(): List<Higene>

    @Query("SELECT * FROM item WHERE ilosc>0 AND klasa=83")
    fun getAllSleepMoreThan0(): List<Sleep>

    @Query("SELECT * FROM item WHERE ilosc>0 AND klasa=90")
    fun getAllFunMoreThan0(): List<Fun>

//    @Query("SELECT * FROM item WHERE ilosc>0")
//    fun getAllItemsMoreThan0(): List<Item>
//
//    @Query("SELECT * FROM item")
//    fun getAllItems(): List<Item>

    @Query("UPDATE item SET ilosc = ilosc+:dodanaIlosc WHERE id = :zmienianeId")
    fun dodajIloscItem(zmienianeId: Int, dodanaIlosc: Int)

    @Query("UPDATE item SET ilosc = ilosc-:odejmowanaIlosc WHERE id = :zmienianeId")
    fun odejmijIloscItem(odejmowanaIlosc: Int, zmienianeId: Int)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllItems(itemy: List<Item>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllRooms(pokoj: List<Pokoj>)

    //@Query("SELECT * FROM pokoj")
    //fun getAllRooms(): List<Pokoj>


    //J-74, S-83, H-72, Z-90
    @Query("SELECT * FROM pokoj WHERE klasaItemu=74")
    fun getKitchen(): Kuchnia
    @Query("SELECT * FROM pokoj WHERE klasaItemu=83")
    fun getSleepinRoom(): Sypialnia
    @Query("SELECT * FROM pokoj WHERE klasaItemu=72")
    fun getBathRoom():Lazienka
    @Query("SELECT * FROM pokoj WHERE klasaItemu=90")
    fun getLivinRoom(): Salon


}




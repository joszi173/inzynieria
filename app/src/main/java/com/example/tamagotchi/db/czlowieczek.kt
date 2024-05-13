package com.example.tamagotchi.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.tamagotchi.Glod

@Entity
class czlowieczek(
    @PrimaryKey val id: Int,
    val imie: String,
    var monety: Int,
    val czasOstatniegokarmienia: Long,
    val wyglad: Bitmap
)
{

    //dodawanie monet
    fun addMoney(amount: Int) {
        monety += amount
    }


}
package com.example.tamagotchi.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class czlowieczek(
    @PrimaryKey val id :Int,
    val imie :String,
    val monety :Int,
    val czasOstatniegokarmienia: Long,
    val wyglad :Bitmap
)

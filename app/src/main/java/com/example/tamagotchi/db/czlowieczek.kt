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
    val monety: Int,
    val czasOstatniegokarmienia: Long,
    val wyglad: Bitmap
)

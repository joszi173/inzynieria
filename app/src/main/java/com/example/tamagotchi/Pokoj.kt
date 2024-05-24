package com.example.tamagotchi

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Pokoj (@PrimaryKey val id: Int,
             val nazwa: String,
             val klasaItemu: Char,
             val tlo: Bitmap
){

}
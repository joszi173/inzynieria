package com.example.tamagotchi.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class czlowieczek(
    @PrimaryKey val id :Int,
    val imie :String
)

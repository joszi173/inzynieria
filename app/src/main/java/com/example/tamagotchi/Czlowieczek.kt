package com.example.tamagotchi

import android.widget.TextView
import android.os.Bundle

class Czlowieczek(val gra:Gra, val imie:String) {


    fun podajImie() {
        val imieCzlowieczka = gra.findViewById<TextView>(R.id.imieCzlowieczka)
        imieCzlowieczka.setText(imie);
    }
}
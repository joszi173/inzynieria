package com.example.tamagotchi

import android.widget.TextView

class Czlowieczek(val gra:Gra, val imie:String) {

fun podajImie() {
    val imieCzlowieczka = gra.findViewById<TextView>(R.id.imieCzlowieczka)
    imieCzlowieczka.setText(imie);
}
}
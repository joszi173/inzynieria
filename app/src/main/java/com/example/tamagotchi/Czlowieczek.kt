package com.example.tamagotchi

import android.widget.TextView
import android.os.Bundle

class Czlowieczek(val gra:Gra, val imie:String) {
    //var glod:Potrzeba;

    fun onCreate(){
        //glod=100;
    }
    public fun karmienie(/*jedzenie:Item/Jedzenie*/){
        //glod+=jedzenie.wartosc
    }

    fun podajImie() {
        val imieCzlowieczka = gra.findViewById<TextView>(R.id.imieCzlowieczka)
        imieCzlowieczka.setText(imie);
    }
}
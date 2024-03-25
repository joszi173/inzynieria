package com.example.tamagotchi

import android.widget.TextView
import android.os.Bundle

class Czlowieczek(val gra:Gra, val imie:String) {
    //var glod:Potrzeba;
    var glod=Glod();

    fun onCreate(){

    }
    public fun karmienie(/*jedzenie:Item/Jedzenie*/){
        //glod+=jedzenie.wartosc
        glod.zwiekszGlod(20);
    }

    fun podajImie() {//fcja tymczasowa, tylko do sprawdzenia czy się obiekt stworzył
        val imieCzlowieczka = gra.findViewById<TextView>(R.id.imieCzlowieczka)
        imieCzlowieczka.setText(imie);
    }
}
package com.example.tamagotchi

import android.widget.TextView
import android.os.Bundle

class Czlowieczek(val gra:Gra, val imie:String, val bd:BDManager) {
    //var glod:Potrzeba;
    var glod=Glod()
    fun onCreate(){
        //glod=100;
    }
    public fun karmienie(/*jedzenie:Item/Jedzenie*/){
        //glod+=jedzenie.wartosc
        glod.zwiekszGlod(20);
        println("C Witaj, świecie!")
        //println(bd.odczytajOstatnieKarmienie().toString())
        bd.zapiszKarmienie()
        //println(bd.odczytajOstatnieKarmienie().toString())

        //println(System.currentTimeMillis())
    }


    ////fcje tymczasowe
    fun podajImie() {
        val imieCzlowieczka = gra.findViewById<TextView>(R.id.imieCzlowieczka)
        imieCzlowieczka.setText(imie);
    }

}
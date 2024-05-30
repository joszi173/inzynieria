package com.example.tamagotchi

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
abstract class Pokoj (@PrimaryKey val id: Int,
             val nazwa: String,
             val klasaItemu: Char,
             val tlo: Bitmap
            //@Ignore val itemy: MutableList<Item>
){
    @Ignore var potrzeba: Potrzeba? = null
    @Ignore var itemy=mutableListOf<Item>()
    open fun onInteract(aktualnyItem:Int):Int{
        return 0
    }
}

open class Kuchnia(id: Int, nazwa: String, klasaItemu: Char, tlo: Bitmap)
    :Pokoj(id, nazwa, klasaItemu, tlo){

    override fun onInteract(aktualnyItem:Int):Int{
        potrzeba?.zwiekszPotrzebe(itemy[aktualnyItem].wartosc)
        itemy[aktualnyItem].ilosc--
        //dao.zapiszCzasKarmienia
        //dao.dodajIloscItem(-1, itemy[aktualnyItem].id)
        if(itemy[aktualnyItem].ilosc<=0) {
            itemy.removeAt(aktualnyItem)
        }
        return 1
    }

}

open class Lazienka(id: Int, nazwa: String, klasaItemu: Char, tlo: Bitmap)
    :Pokoj(id, nazwa, klasaItemu, tlo){

    override fun onInteract(aktualnyItem:Int):Int{
        potrzeba?.zwiekszPotrzebe(itemy[aktualnyItem].wartosc)
        //itemy[aktualnyItem].ilosc--
        //dao.zapiszCzasKarmienia
        //dao.dodajIloscItem(-1, itemy[aktualnyItem].id)
        if(itemy[aktualnyItem].ilosc<=0) {
            itemy.removeAt(aktualnyItem)
        }
        return 0
    }

}
open class Salon(id: Int, nazwa: String, klasaItemu: Char, tlo: Bitmap)
    :Pokoj(id, nazwa, klasaItemu, tlo){

    override fun onInteract(aktualnyItem:Int):Int{
        potrzeba?.zwiekszPotrzebe(itemy[aktualnyItem].wartosc)
        //itemy[aktualnyItem].ilosc--
        //dao.zapiszCzasKarmienia
        //dao.dodajIloscItem(-1, itemy[aktualnyItem].id)
        if(itemy[aktualnyItem].ilosc<=0) {
            itemy.removeAt(aktualnyItem)
        }
        return 0
    }

}
open class Sypialnia(id: Int, nazwa: String, klasaItemu: Char, tlo: Bitmap)
    :Pokoj(id, nazwa, klasaItemu, tlo){

    override fun onInteract(aktualnyItem:Int):Int{
        potrzeba?.zwiekszPotrzebe(itemy[aktualnyItem].wartosc)
        //itemy[aktualnyItem].ilosc--
        //dao.zapiszCzasKarmienia
        //dao.dodajIloscItem(-1, itemy[aktualnyItem].id)
        if(itemy[aktualnyItem].ilosc<=0) {
            itemy.removeAt(aktualnyItem)
        }
        return 0
    }


}
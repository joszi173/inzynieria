package com.example.tamagotchi

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.tamagotchi.db.tamagotchiDao


class Domek(val dao: tamagotchiDao, var glod: Potrzeba, val gra:Gra, val pokoje:List<Pokoj>, var aktualnyPokoj:Int=0) : Fragment(R.layout.fragment_domek) {

    var tlo: RelativeLayout? =null
    var przyciskPokojL:Button? =null
    var przyciskPokojR:Button? =null
    var przyciskItemL:Button?= null
    var przyciskItemR:Button?= null
    var przyciskItemu:Button? = null
    var listaItemow= mutableListOf<Item>()
    var aktualnyItem = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tlo=getView()?.findViewById<RelativeLayout>(R.id.tloPokojuWDomku)
        UstawTloNaAktualnegoPokoju()

        //ustawianie przycisków do zmiany pokoju
        ///////////////////////////
        przyciskPokojL = getView()?.findViewById<Button>(R.id.buttonPokojL)
        przyciskPokojR = getView()?.findViewById<Button>(R.id.buttonPokojR)
        przyciskPokojL?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                ZmienPokoj(-1)
            }
        })
        przyciskPokojR?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                ZmienPokoj(1)
            }
        })
        /////////////////////////////////


        //ustawianie czlowieczka
        //////////////////////////////////
        val img = dao.getAllCz().first().wyglad
        val czlowieczekUIIMG = getView()?.findViewById<ImageView>(R.id.czlowieczekUIIMG)
        if (czlowieczekUIIMG != null) {
            czlowieczekUIIMG.setImageBitmap(img)
        }
        if (czlowieczekUIIMG != null) {
            czlowieczekUIIMG.setOnClickListener()
            {
                dao.dodajMonety(50)
                println("Monety: "+dao.getAllCz().first().monety)
                gra.ZmienWyswietlaneMonety(dao.getAllCz().first().monety)
            }
        }
        /////////////////////////






        //ustawianie Itemów
        /////////////////////////////////////
        println("pokoj "+pokoje[aktualnyPokoj].nazwa)
        //val listaJedzenia = dao.getAllFoodMoreThan0().toMutableList()
        //for(f:Food in listaJedzenia){
        //   println("jedzenie "+f.id.toString()+", ilosc: "+f.ilosc.toString()+", klasa: "+f.klasa.toString())
        //}


        /////////////////////////////////////
         przyciskItemL= getView()?.findViewById<Button>(R.id.buttonItemL)
         przyciskItemR= getView()?.findViewById<Button>(R.id.buttonItemR)
         przyciskItemu = getView()?.findViewById<Button>(R.id.UzyjItemu)
        ///////////////////////////


        /*
        if(listaJedzenia.count()>0){
            if (przyciskItemu != null) {
                //
                przyciskItemu!!.background=BitmapDrawable(getResources(),
                    listaJedzenia.get(aktualnyItem).bitmap
                )
                przyciskItemu!!.text = listaJedzenia.get(aktualnyItem).ilosc.toString()}}
        */
        WczytajItemyDoSlotu()

        przyciskItemu?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                UzyjItemu()
                /*
                if(listaJedzenia.size<=0){
                    println("ni ma jedzenia")
                    return
                }

                listaJedzenia.get(aktualnyItem).let { glod.zwiekszGlod(it.wartosc) }
                println("Stary czas karmienia "+dao.getAllCz().first().czasOstatniegokarmienia)

                dao.updateCzasKarmienia(System.currentTimeMillis())
                println("Nowy czas karmienia "+dao.getAllCz().first().czasOstatniegokarmienia)
                println("Karmienie " + (listaJedzenia[aktualnyItem]))
                println("Nowy czas karmienia " )


                listaJedzenia.get(aktualnyItem).ilosc = listaJedzenia.get(aktualnyItem).ilosc - 1
                dao.dodajIloscItem(-1, listaJedzenia[aktualnyItem].id)
                dao.insertAllItems(listaJedzenia.toList())
                przyciskItemu!!.text = listaJedzenia.get(aktualnyItem).ilosc.toString()
                if(listaJedzenia[aktualnyItem].ilosc<=0){
                    if(listaJedzenia.count()>0) {
                        listaJedzenia.removeAt(aktualnyItem)

                    }
                    if(listaJedzenia.count()<=0){
                        println("brak jedzenia")
                        przyciskItemu!!.text = "X"
                        przyciskItemu!!.background=BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.button))
                        przyciskItemu!!.isClickable=false
                        przyciskItemL?.isClickable=false
                        przyciskItemR?.isClickable=false
                        return
                    }
                }*/

            }
        })
////////////////////////////////////////////////



            //ustawianie przyciskow do zmiany jedzenia
////////////////////////////////////////////////
        przyciskItemL?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                PrzełączItem(-1)
            }
        })
        przyciskItemR?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                PrzełączItem(1)
            }

        })
///////////////////////////////////////////////



        }

    fun UstawTloNaAktualnegoPokoju(){
        if (tlo != null) {
            tlo!!.background=BitmapDrawable(getResources(), pokoje[aktualnyPokoj].tlo)
        }
    }

    fun ZmienPokoj(numer:Int){
        aktualnyPokoj+=numer
        if(aktualnyPokoj<0){
            aktualnyPokoj=pokoje.size-1
        }else if(aktualnyPokoj>=pokoje.size){
            aktualnyPokoj=0
        }
        UstawTloNaAktualnegoPokoju()
    }

    ////item handling

    fun UstawWygladPrzyciskuItemu(){
        przyciskItemu?.background=BitmapDrawable(getResources(), listaItemow[aktualnyItem].bitmap)
        przyciskItemu?.text=listaItemow[aktualnyItem].ilosc.toString()
    }

    fun WczytajItemyDoSlotu(){
        when(pokoje[aktualnyPokoj].klasaItemu){
            'J'->listaItemow=dao.getAllFoodMoreThan0().toMutableList()
            //dodać następne dla kolejnych klas itemów
        }
        if(listaItemow.size<=0){
            przyciskItemu?.isClickable=false
            przyciskItemL?.isClickable=false
            przyciskItemL?.isClickable=false
            return
        }
        UstawWygladPrzyciskuItemu()
    }

    fun PrzełączItem(numer:Int){

        aktualnyItem+=numer
        if(aktualnyItem<0){
            aktualnyItem=listaItemow.size-1
        }else if(aktualnyItem>=listaItemow.size){
            aktualnyItem=0
        }
        UstawWygladPrzyciskuItemu()
    }

    fun UzyjItemu(){
        listaItemow[aktualnyItem].onInteract(glod)
        dao.updateCzasKarmienia(System.currentTimeMillis())

        listaItemow[aktualnyItem].ilosc--
        dao.dodajIloscItem(-1, listaItemow[aktualnyItem].id)
        if(listaItemow[aktualnyItem].ilosc<=0){
            listaItemow.removeAt(aktualnyItem)
            if(!WylaczPrzyciskJesliBrakItemow()){
                PrzełączItem(1)
            }
        }
        przyciskItemu?.text= listaItemow[aktualnyItem].ilosc.toString()

    }

    fun WylaczPrzyciskJesliBrakItemow():Boolean{
        if(listaItemow.size<=0){
            przyciskItemu!!.background=BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.button))
            przyciskItemu?.isClickable=false
            przyciskItemL?.isClickable=false
            przyciskItemR?.isClickable=false
            return true
        }
        return false
    }

}

/*

 */
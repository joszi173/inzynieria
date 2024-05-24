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


class Domek(val dao: tamagotchiDao, var glod: Glod, val gra:Gra, val pokoje:List<Pokoj>, var aktualnyPokoj:Int=0) : Fragment(R.layout.fragment_domek) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tlo=getView()?.findViewById<RelativeLayout>(R.id.tloPokojuWDomku)
        if (tlo != null) {
            tlo.background=BitmapDrawable(getResources(), pokoje[aktualnyPokoj].tlo)
        }

        val przyciskPokojL = getView()?.findViewById<Button>(R.id.buttonPokojL)
        val przyciskPokojR = getView()?.findViewById<Button>(R.id.buttonPokojR)

        if (przyciskPokojL != null) {
            przyciskPokojL.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                aktualnyPokoj--
                    if(aktualnyPokoj<0){
                        aktualnyPokoj=pokoje.size-1
                    }
                    if (tlo != null) {
                        tlo.background=BitmapDrawable(getResources(), pokoje[aktualnyPokoj].tlo)
                    }

                }
            })
        }

        if (przyciskPokojR != null) {
            przyciskPokojR.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    aktualnyPokoj++
                    if(aktualnyPokoj>=pokoje.size){
                        aktualnyPokoj=0
                    }
                    if (tlo != null) {
                        tlo.background=BitmapDrawable(getResources(), pokoje[aktualnyPokoj].tlo)
                    }

                }
            })
        }


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
        println("pokoj "+pokoje[aktualnyPokoj].nazwa)
        val listaJedzenia = dao.getAllFoodMoreThan0().toMutableList()
        for(f:Food in listaJedzenia){
            println("jedzenie "+f.id.toString()+", ilosc: "+f.ilosc.toString()+", klasa: "+f.klasa.toString())
        }
        var aktualnyItem = 0

        val przyciskJedzenieL= getView()?.findViewById<Button>(R.id.buttonItemL)
        val przyciskJedzenieR= getView()?.findViewById<Button>(R.id.buttonItemR)
        val przyciskKarmienia = getView()?.findViewById<Button>(R.id.UzyjItemu)
        if(listaJedzenia.count()>0){
            if (przyciskKarmienia != null) {
                //
                przyciskKarmienia.background=BitmapDrawable(getResources(),
                    listaJedzenia.get(aktualnyItem).bitmap
                )
                przyciskKarmienia.text = listaJedzenia.get(aktualnyItem).ilosc.toString()}}
        if (przyciskKarmienia != null) {
        przyciskKarmienia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
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
                przyciskKarmienia.text = listaJedzenia.get(aktualnyItem).ilosc.toString()
                if(listaJedzenia[aktualnyItem].ilosc<=0){
                    if(listaJedzenia.count()>0) {
                        listaJedzenia.removeAt(aktualnyItem)

                    }
                    if(listaJedzenia.count()<=0){
                        println("brak jedzenia")
                        przyciskKarmienia.text = "X"
                        przyciskKarmienia.background=BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.button))
                        przyciskKarmienia.isClickable=false
                        przyciskJedzenieL?.isClickable=false
                        przyciskJedzenieR?.isClickable=false
                        return
                    }
                }

                }
        })
        }


        if (przyciskJedzenieL != null) {
            przyciskJedzenieL.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    //if(listaJedzenia.size<=0) {
                    aktualnyItem--
                    if (aktualnyItem < 0) {
                        aktualnyItem = listaJedzenia.count()-1

                    }
                    if (przyciskKarmienia != null) {
                        przyciskKarmienia.text = listaJedzenia[aktualnyItem].ilosc.toString()

                    przyciskKarmienia.background=BitmapDrawable(getResources(), listaJedzenia[aktualnyItem].bitmap)
                    }
                //}else{
                 //   println("nie ma innego jedzenia")

                //}
                }

            })
            }
        if (przyciskJedzenieR != null) {
            przyciskJedzenieR.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    //if(listaJedzenia.size<=0) {
                        aktualnyItem++
                        if (aktualnyItem >= listaJedzenia.count()) {
                            aktualnyItem = 0

                        }
                        if (przyciskKarmienia!=null) {
                            przyciskKarmienia.text = listaJedzenia[aktualnyItem].ilosc.toString()
                            przyciskKarmienia.background =
                                BitmapDrawable(getResources(), listaJedzenia[aktualnyItem].bitmap)
                        }
                    //}else{
                        //println("nie ma innego jedzenia")

                    //}
                }

            })
        }


        }}

/*

 */
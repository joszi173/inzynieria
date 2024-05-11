package com.example.tamagotchi

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class Domek(var glod: Glod) : Fragment(R.layout.fragment_domek) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //odczytanie obrazu z bazy i wyświetlenie na ekran

        val dao = context?.let { tamagotchiDatabase.getInstance(it.applicationContext).tamagotchiDao }

        val img = dao?.getAllCz()?.first()?.wyglad

        //val img = mbd.odczytajObraz()
        val czlowieczekUIIMG = getView()?.findViewById<ImageView>(R.id.czlowieczekUIIMG)
        if (czlowieczekUIIMG != null) {
            czlowieczekUIIMG.setImageBitmap(img)
        }


        var listaJedzenia = dao?.getAllGdzieWiecejNiz0()?.toMutableList()
        var aktualnyItem = 1


        val przyciskKarmienia = getView()?.findViewById<Button>(R.id.UzyjItemu)
        if (przyciskKarmienia != null) {
            przyciskKarmienia.background=BitmapDrawable(getResources(), listaJedzenia?.get(aktualnyItem)?.bitmap)

        przyciskKarmienia.text = listaJedzenia?.get(aktualnyItem)?.ilosc.toString()
        przyciskKarmienia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                listaJedzenia?.get(aktualnyItem)?.let { glod.zwiekszGlod(it.wartosc) }
                //listaJedzenia?.get(aktualnyItem)?.onInteract(glod)
                if (dao != null) {
                    println("Stary czas karmienia "+dao.getAllCz().first().czasOstatniegokarmienia)

                dao.updateCzasKarmienia(System.currentTimeMillis())
                println("Nowy czas karmienia "+dao.getAllCz().first().czasOstatniegokarmienia)
                // mbd.zapiszKarmienie()
                println("Karmienie " + (listaJedzenia?.get(aktualnyItem)))
                println("Nowy czas karmienia " )//+ mbd.odczytajOstatnieKarmienie())
                }

                listaJedzenia?.get(aktualnyItem)?.ilosc = listaJedzenia?.get(aktualnyItem)?.ilosc!! - 1
                if (dao != null) {
                    dao.insertAll(listaJedzenia.toList())
                }
                if(listaJedzenia[aktualnyItem].ilosc<=0){
                    if(listaJedzenia.count()>0) {
                        listaJedzenia.removeAt(aktualnyItem)
                    }
                    if(listaJedzenia.count()<=0){
                        println("brak jedzenia")
                        przyciskKarmienia.text = "X"
                        przyciskKarmienia.background=BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.button))
                        przyciskKarmienia.isClickable=false
                        return
                    }
                }

                //tymczasowa zmiana indeksu do testu
                aktualnyItem++
                if (aktualnyItem >= listaJedzenia.count()) {
                    aktualnyItem = 0

                }
                przyciskKarmienia.text = listaJedzenia[aktualnyItem].ilosc.toString()
                przyciskKarmienia.background=BitmapDrawable(getResources(), listaJedzenia[aktualnyItem].bitmap)
            }
        })
        }

    }


}

/*

 */
package com.example.tamagotchi

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.example.tamagotchi.db.tamagotchiDao

class Sklepik(val dao: tamagotchiDao, val gra: Gra) : Fragment(R.layout.fragment_sklepik) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layout = view.findViewById<TableLayout>(R.id.tabelaZItememi)

        val listaItemow = dao.getAllFood()

        var itemCount = 0

        var newRow = TableRow(context)

        for (i in listaItemow.listIterator()) {
            val nowyPrzycisk = Button(context)
            println("tworze obiekt, koszt:" + i.koszt.toString())

            nowyPrzycisk.background = i.bitmap.toDrawable(resources)
            nowyPrzycisk.id = i.id

            val cenaLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                setPadding(5)
                setBackgroundResource(R.drawable.border) // Dodaj plik border.xml do res/drawable
            }

            val cenaTextView = TextView(context).apply {
                text = i.koszt.toString()
                setTextColor(Color.parseColor("black"))
                textSize = 30f // Zmniejszenie rozmiaru tekstu
                gravity = Gravity.CENTER
            }

            val moneyImageView = ImageView(context).apply {
                setImageResource(R.drawable.money)
                layoutParams = LinearLayout.LayoutParams(100, 100).apply {
                    setMargins(0, 0, 0, 0) // Zmniejszenie rozmiaru obrazka i ustawienie marginesów
                }
            }
            cenaLayout.addView(cenaTextView)
            cenaLayout.addView(moneyImageView)

            val itemLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(10)
                addView(nowyPrzycisk)
                addView(cenaLayout)
            }

            newRow.addView(itemLayout)

            itemCount++

            nowyPrzycisk.setOnClickListener {
                if (dao.getAllCz().first().monety >= i.koszt) {
                    dao.dodajIloscItem(nowyPrzycisk.id, 1)
                    dao.dodajMonety(-i.koszt)
                    println("dodaje item " + nowyPrzycisk.id.toString() + " o koszcie " + i.koszt)
                    gra.ZmienWyswietlaneMonety(dao.getAllCz().first().monety)
                } else {
                    println("brak monet")
                }
            }

            if (itemCount == 3) {
                // Dodanie bieżącego wiersza do tabeli
                layout.addView(newRow)

                // Utworzenie nowego wiersza i resetowanie licznika przedmiotów
                newRow = TableRow(context)
                itemCount = 0
            }
        }

        // Dodanie ostatniego wiersza, jeśli zawiera jakiekolwiek przedmioty
        if (itemCount > 0) {
            layout.addView(newRow)
        }
    }
}

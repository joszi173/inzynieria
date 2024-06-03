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

        // Znajdź TableLayout w widoku
        val layout = view.findViewById<TableLayout>(R.id.tabelaZItememi)

        // Pobierz listę przedmiotów z bazy danych
        val listaItemow = dao.getAllFood()

        // Zmienna do śledzenia liczby elementów w bieżącym wierszu
        var itemCount = 0

        // Obecny wiersz do dodawania przycisków
        var newRow = TableRow(context)

        // Iteruj przez listę przedmiotów
        for (i in listaItemow.listIterator()) {
            // Tworzenie nowego przycisku dla każdego przedmiotu
            val nowyPrzycisk = Button(context)
            println("tworze obiekt, koszt:" + i.koszt.toString())

            // Ustawienie tła przycisku na bitmapę przedmiotu
            nowyPrzycisk.background = i.bitmap.toDrawable(resources)
            nowyPrzycisk.id = i.id

            // Tworzenie LinearLayout dla ceny i obrazka
            val cenaLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                setPadding(5)
                setBackgroundResource(R.drawable.border) // Dodaj plik border.xml do res/drawable
            }

            // Tworzenie TextView dla ceny przedmiotu
            val cenaTextView = TextView(context).apply {
                text = i.koszt.toString()
                setTextColor(Color.parseColor("#FF0033"))
                textSize = 20f // Zmniejszenie rozmiaru tekstu
                gravity = Gravity.CENTER
            }

            // Tworzenie ImageView dla obrazka money.png
            val moneyImageView = ImageView(context).apply {
                setImageResource(R.drawable.money)
                layoutParams = LinearLayout.LayoutParams(120, 120).apply {
                    setMargins(0, -10, 0, 0) // Zmniejszenie rozmiaru obrazka i ustawienie marginesów
                }
            }

            // Dodanie TextView i ImageView do LinearLayout
            cenaLayout.addView(cenaTextView)
            cenaLayout.addView(moneyImageView)

            // Dodanie przycisku i LinearLayout do layoutu pionowego (LinearLayout)
            val itemLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(10)
                addView(nowyPrzycisk)
                addView(cenaLayout)
            }

            // Dodanie layoutu pionowego do bieżącego wiersza
            newRow.addView(itemLayout)

            // Zwiększenie licznika przedmiotów w wierszu
            itemCount++

            // Ustawienie OnClickListener dla przycisku
            nowyPrzycisk.setOnClickListener {
                // Sprawdzenie, czy użytkownik ma wystarczająco dużo monet
                if (dao.getAllCz().first().monety >= i.koszt) {
                    // Dodanie przedmiotu do ekwipunku i odjęcie monet
                    dao.dodajIloscItem(nowyPrzycisk.id, 1)
                    dao.dodajMonety(-i.koszt)
                    println("dodaje item " + nowyPrzycisk.id.toString() + " o koszcie " + i.koszt)

                    // Aktualizacja wyświetlanych monet
                    gra.ZmienWyswietlaneMonety(dao.getAllCz().first().monety)
                } else {
                    println("brak monet")
                }
            }

            // Sprawdzenie, czy bieżący wiersz ma już maksymalną liczbę przedmiotów (np. 3)
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

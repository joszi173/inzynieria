package com.example.tamagotchi

import android.content.Context
import showNoti

class Potrzeba(var context: Context, var level: Int = 100) {

    private var pasekPotrzebyListener: PasekPotrzebyListener? = null
    var czy_noti: Boolean = true

    init {
        startDecreaseThread()

    }

    interface PasekPotrzebyListener {
        fun onPasekPotrzebyChange(bitmapResource: Int)
    }

    fun setPasekPotrzebyListener(listener: PasekPotrzebyListener) {
        this.pasekPotrzebyListener = listener
    }

    private fun startDecreaseThread() {
        Thread {
            while (true) {
                Thread.sleep(10000) // czekaj 10 sek
                zmniejszPasek()
            }
        }.start()
    }

    @Synchronized
    private fun zmniejszPasek() {
        if (level > 0) {
            level -= 2
            if (level % 10 == 0) {
                // Wywołujemy metodę zmieniającą obraz paska głodu co 10 poziomów
                pasekPotrzebyListener?.onPasekPotrzebyChange(getObrazDlaPoziomu(level))

            }

            if (level < 50 && czy_noti) {
                showNoti(context.applicationContext, "niski poziom potrzeby", "Niski poziom potrzeby")
                czy_noti = false
            }


            println(level)

        }
    }

    fun jakBardzo(): Int {
        return level
    }

    fun zwiekszPotrzebe(amount: Int) {
        if (level < 100) {
            level = minOf(100, level + amount) // Zwiększ poziom głodu
        }
        if (level % 10 == 0) {
            // Wywołujemy metodę zmieniającą obraz paska głodu co 10 poziomów
            pasekPotrzebyListener?.onPasekPotrzebyChange(getObrazDlaPoziomu(level))

        }

        if (level > 50)
            czy_noti = true
        //println("G Witaj, świecie!")
    }

    // Metoda do mapowania poziomu na odpowiedni obraz paska głodu
    private fun getObrazDlaPoziomu(level: Int): Int {
        return when (level) {
            in 99..100 -> R.drawable.pasek_glod_10
            in 80..98 -> R.drawable.pasek_glod_9
            in 70..79 -> R.drawable.pasek_glod_8
            in 60..69 -> R.drawable.pasek_glod_7
            in 50..59 -> R.drawable.pasek_glod_6
            in 40..49 -> R.drawable.pasek_glod_5
            in 30..39 -> R.drawable.pasek_glod_4
            in 20..29 -> R.drawable.pasek_glod_3
            in 10..19 -> R.drawable.pasek_glod_2
            in 9..1 -> R.drawable.pasek_glod_1
            else -> R.drawable.pasek_glod_0
        }
    }
}

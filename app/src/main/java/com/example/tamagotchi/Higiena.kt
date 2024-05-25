package com.example.tamagotchi

import android.content.Context
import showNoti


class Higiena(var context: Context, var level: Int = 100) {

    private var pasekHigienyListener: PasekHigienyListener? = null
    var czy_noti: Boolean = true

    init {
        startDecreaseThread()

    }

    interface PasekHigienyListener {
        fun onPasekHigienyChange(bitmapResource: Int)
    }

    fun setPasekHigienyListener(listener: PasekHigienyListener) {
        this.pasekHigienyListener = listener
    }

    private fun startDecreaseThread() {
        Thread {
            while (true) {
                Thread.sleep(500) // czekaj 0.5 sek
                zmniejszPasek()
            }
        }.start()
    }

    @Synchronized
    private fun zmniejszPasek() {
        if (level > 0) {
            level -= 2
            if (level % 10 == 0) {
                pasekHigienyListener?.onPasekHigienyChange(getObrazDlaPoziomu(level))

            }

            if (level < 50 && czy_noti) {
                showNoti(context.applicationContext, "Tamagotchi jest brudny", "Umyj mnie!")
                czy_noti = false
            }


            println(level)

        }
    }

    fun jakBrudny(): Int {
        return level
    }

    fun zwiekszHigiene(amount: Int) {
        if (level < 100) {
            level = minOf(100, level + amount) // Zwiększ poziom higieny
        }
        if (level % 10 == 0) {
            // Wywołujemy metodę zmieniającą obraz paska zabawy co 10 poziomów
            pasekHigienyListener?.onPasekHigienyChange(getObrazDlaPoziomu(level))

        }

        if (level > 50)
            czy_noti = true
        //println("G Witaj, świecie!")
    }

    // Metoda do mapowania poziomu na odpowiedni obraz paska higieny
    private fun getObrazDlaPoziomu(level: Int): Int {
        return when (level) {
            in 99..100 -> R.drawable.pasek_full10
            in 80..98 -> R.drawable.pasek_9
            in 70..79 -> R.drawable.pasek_8
            in 60..69 -> R.drawable.pasek_7
            in 50..59 -> R.drawable.pasek_6
            in 40..49 -> R.drawable.pasek_5
            in 30..39 -> R.drawable.pasek_4
            in 20..29 -> R.drawable.pasek_3
            in 10..19 -> R.drawable.pasek_2
            in 9..1 -> R.drawable.pasek_1
            else -> R.drawable.pasek_0
        }
    }
}
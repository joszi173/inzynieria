package com.example.tamagotchi

import showNoti

class Glod(var level: Int = 100) {

    private var pasekGloduListener: PasekGloduListener? = null

    init {
        startDecreaseThread()
    }

    interface PasekGloduListener {
        fun onPasekGloduChange(bitmapResource: Int)
    }

    fun setPasekGloduListener(listener: PasekGloduListener) {
        this.pasekGloduListener = listener
    }

    private fun startDecreaseThread() {
        Thread {
            while (true) {
                Thread.sleep(1000) // czekaj 1 sekundę
                zmniejszPasek()
            }
        }.start()
    }

    @Synchronized
    private fun zmniejszPasek() {
        if (level > 0) {
            level -= 1
            if (level % 10 == 0) {
                // Wywołujemy metodę zmieniającą obraz paska głodu co 10 poziomów
                pasekGloduListener?.onPasekGloduChange(getObrazDlaPoziomu(level))

            }
            println(level)

        }
    }

    fun jakGlodny(): Int {
        return level
    }

    fun zwiekszGlod(amount: Int) {
        if (level < 100) {
            level = minOf(100, level + amount) // Zwiększ poziom głodu
        }
        //println("G Witaj, świecie!")
    }

    // Metoda do mapowania poziomu na odpowiedni obraz paska głodu
    private fun getObrazDlaPoziomu(level: Int): Int {
        return when (level) {
            in 90..100 -> R.drawable.pasek_full10
            in 80..89 -> R.drawable.pasek_9
            in 70..79 -> R.drawable.pasek_8
            in 60..69 -> R.drawable.pasek_7
            in 50..59 -> R.drawable.pasek_6
            in 40..49 -> R.drawable.pasek_5
            in 30..39 -> R.drawable.pasek_4
            in 20..29 -> R.drawable.pasek_3
            in 10..19 -> R.drawable.pasek_2
            else -> R.drawable.pasek_1
        }
    }
}

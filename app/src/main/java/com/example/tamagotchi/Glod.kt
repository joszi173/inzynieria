package com.example.tamagotchi
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlin.concurrent.thread


class Glod(val pasekGlod: ImageView,val pasekGlodFull: Bitmap,val pasekGlodEmpty: Bitmap, var level: Int = 100) {

    var imageResource=pasekGlodFull
    init {
        startDecreaseThread()
        //Greeting(level)
    }

    private fun startDecreaseThread() {
        thread(start = true) {
            while (true) {
                Thread.sleep(1000) // czekaj 1 sekundę
                zmniejszPasek()
                //Greeting(level)
                //pasekGlod.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.czlowieczek1))
            }
        }
    }

    @Synchronized
    fun zmniejszPasek() {
        if (level > 0) {
            level -= 1
            if(level<50){
                pasekGlod.setImageBitmap(pasekGlodEmpty)
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
            if(level>50){
                pasekGlod.setImageBitmap(pasekGlodFull)
            }

        }

        println("G Witaj, świecie!")

    }
/*
    fun Greeting(/*name: String,*/ hungerLevel: Int) {

        imageResource = when (hungerLevel) {
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
    }*/
}




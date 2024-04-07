package com.example.tamagotchi
import kotlin.concurrent.timer
class Glod(var level: Int = 100) {

    fun zmniejszPasek() {
        if (level > 0) {
            level -= 1
        }
    }

    fun jakGlodny(): Int {
        return level
    }

    fun zwiekszGlod(amount: Int) {
        if (level < 100) {
            level = minOf(100, level + amount) // Zwiększ poziom głodu

        }

        println("G Witaj, świecie!")

    }
}




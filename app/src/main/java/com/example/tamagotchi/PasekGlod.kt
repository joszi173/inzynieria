package com.example.tamagotchi
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.fixedRateTimer
class PasekGlod : AppCompatActivity(){




        private lateinit var hunger: Glod
        private lateinit var hungerProgressBar: ProgressBar
        private lateinit var hungerTextView: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_gra)

            hunger = Glod()

            hungerProgressBar = findViewById(R.id.progressBar)
            hungerTextView = findViewById(R.id.textView)

            val handler = Handler(Looper.getMainLooper())
            handler.post(object : Runnable {
                override fun run() {
                    updateHunger()
                    handler.postDelayed(this, 60) // Aktualizuj co sek(6000 milisekund)
                }
            })
        }

        private fun updateHunger() {
            hunger.zmniejszPasek()
            val hungerLevel = hunger.jakGlodny()
            hungerProgressBar.progress = hungerLevel
            hungerTextView.text = "Poziom głodu: $hungerLevel"
        }
    }


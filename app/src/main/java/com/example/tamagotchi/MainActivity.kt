package com.example.tamagotchi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tamagotchi.ui.theme.TamagotchiTheme
import com.example.tamagotchi.Glod

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TamagotchiTheme {
                val hungerLevel = Glod().jakGlodny()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Greeting(name = "Android", hungerLevel = hungerLevel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, hungerLevel: Int) {
    val imageResource = when (hungerLevel) {
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

    Surface()
    {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TamagotchiTheme {
        Greeting("Android", hungerLevel = 85)
    }
}




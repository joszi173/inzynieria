package com.example.tamagotchi

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tamagotchi.Potrzeba
// com.example.tamagotchi.Czlowieczek
import com.example.tamagotchi.R

// Abstrakcyjna klasa bazowa reprezentująca przedmiot w grze
@Entity
open/*abstract*/ class Item(/*context: Context, attrs: AttributeSet?, */@PrimaryKey val id:Int, var ilosc:Int, val wartosc: Int, val bitmap: Bitmap, val koszt:Int, val klasa:Char)/* : View(context, attrs, wartosc) */{


    /*
        protected val paint = Paint()
        //MEOW!!!!

        //koordynaty poczatkowe - środek
        // Przesunięcie przedmiotu w osi X
        protected var offsetX = 0f
        // Przesunięcie przedmiotu w osi Y
        protected var offsetY = 0f
        // Ostatnia pozycja dotknięcia na osi X
        protected var lastTouchX = 0f
        // Ostatnia pozycja dotknięcia na osi Y
        protected var lastTouchY = 0f

        //ustawianie nowych wspolrzednych - domyslnie: środek ekranu
        fun ustaw(x: Float, y: Float) {
            offsetX = x
            offsetY = y
            invalidate()
        }




        protected var isMoving = false
        protected abstract val itemBitmap: Bitmap



        // rysowanie
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            // Obliczenie pozycji przedmiotu na płótnie
            val x = (width - itemBitmap.width) / 2f + offsetX
            val y = (height - itemBitmap.height) / 2f + offsetY

            // Narysowanie bitmapy przedmiotu na płótnie
            canvas.drawBitmap(itemBitmap, x, y, paint)
        }


        // Metoda abstrakcyjna sprawdzająca, czy dotknięcie znajduje się wewnątrz przedmiotu
        protected abstract fun isInsideItem(x: Float, y: Float): Boolean

        */
    //Interakcja, domyslnie nic nie robi
    open fun onInteract(potrzeba: Potrzeba) {
        // tutaj nadpisywana przez klasy poch
    }



}

// Klasa reprezentująca jedzenie, dziedzicząca po Item
open class Food(/*context: Context, attrs: AttributeSet?,*/  id:Int, ilosc:Int, wartosc: Int, bitmap: Bitmap, koszt:Int, klasa:Char='J') :
    Item(id,ilosc, wartosc, bitmap, koszt, klasa)/*(context, attrs, wartosc)*/
{
   /* override val itemBitmap: Bitmap = bitmap

    fun getBitmap() : Bitmap
    {
        return itemBitmap
    }

    // Poruszanie za pomoca kursora
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isInsideItem(event.x, event.y)) {


                    // Zapamiętanie pozycji dotknięcia

                    lastTouchX = event.x
                    lastTouchY = event.y
                    isMoving = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isMoving) {
                    // Obliczenie przesunięcia przedmiotu
                    offsetX += event.x - lastTouchX
                    offsetY += event.y - lastTouchY
                    lastTouchX = event.x
                    lastTouchY = event.y
                    // Odrysowanie widoku
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                isMoving = false
            }
        }
        return true
    }



    // Metoda sprawdzająca, czy dotknięcie znajduje się wewnątrz jedzenia
    override fun isInsideItem(x: Float, y: Float): Boolean {
        val centerX = (width - itemBitmap.width) / 2f + offsetX
        val centerY = (height - itemBitmap.height) / 2f + offsetY
        val left = centerX
        val top = centerY
        val right = centerX + itemBitmap.width
        val bottom = centerY + itemBitmap.height
        return x >= left && x <= right && y >= top && y <= bottom
    }

*/

    //metoda przeciazajaca metode z item
    override fun onInteract(potrzeba: Potrzeba) {
        potrzeba.zwiekszPotrzebe(wartosc)
    }

}

open class Higene(/*context: Context, attrs: AttributeSet?,*/  id:Int, ilosc:Int, wartosc: Int, bitmap: Bitmap, koszt:Int, klasa:Char='H') :
    Item(id,ilosc, wartosc, bitmap, koszt, klasa)/*(context, attrs, wartosc)*/
{


    //metoda przeciazajaca metode z item
    override fun onInteract(potrzeba: Potrzeba) {
        potrzeba.zwiekszPotrzebe(wartosc)
    }

}

open class Sleep(/*context: Context, attrs: AttributeSet?,*/  id:Int, ilosc:Int, wartosc: Int, bitmap: Bitmap, koszt:Int, klasa:Char='S') :
    Item(id,ilosc, wartosc, bitmap, koszt, klasa)/*(context, attrs, wartosc)*/
{


    //metoda przeciazajaca metode z item
    override fun onInteract(potrzeba: Potrzeba) {
        potrzeba.zwiekszPotrzebe(wartosc)
    }

}

open class Fun(/*context: Context, attrs: AttributeSet?,*/  id:Int, ilosc:Int, wartosc: Int, bitmap: Bitmap, koszt:Int, klasa:Char='Z') :
    Item(id,ilosc, wartosc, bitmap, koszt, klasa)/*(context, attrs, wartosc)*/
{

    //metoda przeciazajaca metode z item
    override fun onInteract(potrzeba: Potrzeba) {
        potrzeba.zwiekszPotrzebe(wartosc)
    }

}

// klasY dziedziczona po jedzeniu + dodanie grafiki
//class Orzeszek(context: Context, attrs: AttributeSet?) : Food(context, attrs, BitmapFactory.decodeResource(context.resources,
   // R.drawable.orzeszek
//))
// class Orange(context: Context, attrs: AttributeSet?) : Food(context, attrs, BitmapFactory.decodeResource(context.resources,
   // R.drawable.orange
//))




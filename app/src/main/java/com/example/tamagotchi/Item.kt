import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.tamagotchi.Czlowieczek
import com.example.tamagotchi.R

// Abstrakcyjna klasa bazowa reprezentująca przedmiot w grze
abstract class Item(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    protected val paint = Paint()


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


    //Interakcja, domyslnie nic nie robi
    open fun onInteract(czlowieczek: Czlowieczek) {
        // tutaj nadpisywana przez klasy poch
    }




}

// Klasa reprezentująca jedzenie, dziedzicząca po Item
open class Food(context: Context, attrs: AttributeSet?, bitmap: Bitmap) : Item(context, attrs)
{
    override val itemBitmap: Bitmap = bitmap


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




    // Metoda interakcji, przeciążająca metodę z klasy bazowej
    override fun onInteract(czlowieczek: Czlowieczek) {
        czlowieczek.karmienie() //wszystkie klasy Food maja karmienie
    }


}



// klasY dziedziczona po jedzeniu + dodanie grafiki
class Orzeszek(context: Context, attrs: AttributeSet?) : Food(context, attrs, BitmapFactory.decodeResource(context.resources,
    R.drawable.orzeszek
))
class Orange(context: Context, attrs: AttributeSet?) : Food(context, attrs, BitmapFactory.decodeResource(context.resources,
    R.drawable.orange
))


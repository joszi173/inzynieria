import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.tamagotchi.R

class Orzeszek(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val normalBitmap: Bitmap
    private val paint = Paint()
    private var offsetX = 0f
    private var offsetY = 0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isMoving = false
    private var lastClickTime: Long = 0

    init {
        normalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.orzeszek)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val x = (width - normalBitmap.width) / 2f + offsetX
        val y = (height - normalBitmap.height) / 2f + offsetY
        canvas.drawBitmap(normalBitmap, x, y, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isInsideOrzeszek(event.x, event.y)) {
                    lastTouchX = event.x
                    lastTouchY = event.y
                    isMoving = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isMoving) {
                    offsetX += event.x - lastTouchX
                    offsetY += event.y - lastTouchY
                    lastTouchX = event.x
                    lastTouchY = event.y
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                isMoving = false
                val clickTime = System.currentTimeMillis()
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    // Podwójne kliknięcie, wywołaj funkcję Dodaj()
                    Dodaj()
                }
                lastClickTime = clickTime
            }
        }
        return true
    }

    private fun isInsideOrzeszek(x: Float, y: Float): Boolean {
        val centerX = (width - normalBitmap.width) / 2f + offsetX
        val centerY = (height - normalBitmap.height) / 2f + offsetY
        val left = centerX
        val top = centerY
        val right = centerX + normalBitmap.width
        val bottom = centerY + normalBitmap.height
        return x >= left && x <= right && y >= top && y <= bottom
    }

    private fun Dodaj() {
        Toast.makeText(context, "PASEK", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 // Interwał czasowy dla podwójnego kliknięcia w milisekundach
    }
}
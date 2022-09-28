package sk.stu.fei.mobv

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    private lateinit var waterAnimation: LottieAnimationView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.add_button).setOnTouchListener {
                _: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startAddWaterToGlass()
                }
                MotionEvent.ACTION_UP -> {
                    stopAnimateWater()
                }
            }
            true
        }
        findViewById<Button>(R.id.remove_button).setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRemoveWaterFromGlass()
                }
                MotionEvent.ACTION_UP -> {
                    stopAnimateWater()
                }
            }
            true
        }
        findViewById<Button>(R.id.pour_out_button).setOnClickListener { pourOutGlass() }

        waterAnimation = findViewById(R.id.animationView)
    }

    private fun pourOutGlass() {
        waterAnimation.progress = 0F;
    }

    private fun startAddWaterToGlass() {
        if (waterAnimation.progress != 1F) {
            waterAnimation.speed = 1F
            waterAnimation.resumeAnimation()
        }
    }

    private fun startRemoveWaterFromGlass() {
        if (waterAnimation.progress != 0F) {
            waterAnimation.speed = -1F
            waterAnimation.resumeAnimation()
        }
    }

    private fun stopAnimateWater() {
        waterAnimation.pauseAnimation()
    }
}
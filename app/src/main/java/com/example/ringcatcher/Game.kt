package com.example.ringcatcher

import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Game : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private var caught: Boolean = false
    private var sank: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val friend = findViewById<ImageView>(R.id.justfriend)
        val ring = findViewById<ImageView>(R.id.ring)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        val randomFloat1 = (50..width-friend.width).random().toFloat()
        val randomFloat2 = (0..height-friend.height).random().toFloat()
        friend.translationX = randomFloat1
        friend.translationY = randomFloat2
        ring.translationX = randomFloat1 + 150.0F
        ring.translationY = randomFloat2

        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let {
            this.accelerometer = it
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.let {
            this.gyroscope = it
        }

        ObjectAnimator.ofFloat(ring, "translationX", 100F).apply {
            duration = 7000
            start()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val firstPosition = IntArray(2)
        val secondPosition = IntArray(2)
        val hand = findViewById<ImageView>(R.id.hand)
        hand.getLocationInWindow(firstPosition)

        val ring = findViewById<ImageView>(R.id.ring)
        ring.getLocationInWindow(secondPosition)

        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                hand.translationX += event.values[0] * 1.5f
                hand.translationY += event.values[1] * 1.5f
            }

            Sensor.TYPE_GYROSCOPE -> {
                hand.translationY += (event.values[0] * 100)
                hand.translationX += (event.values[1] * 100)
            }
        }

        if(secondPosition[0] < 200 && !caught) {
            val result = findViewById<TextView>(R.id.result)
            result.text = getString(R.string.resultBad)
            result.visibility = View.VISIBLE
            sank = true
        }

        if(firstPosition[0] in secondPosition[0]-25..secondPosition[0]+25 && firstPosition[1] in secondPosition[1]-25..secondPosition[1]+25 && !sank) {
            val result = findViewById<TextView>(R.id.result)
            result.text = getString(R.string.you_caught_it)
            result.visibility = View.VISIBLE
            caught = true
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, accelerometer)
        sensorManager.unregisterListener(this, gyroscope)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}
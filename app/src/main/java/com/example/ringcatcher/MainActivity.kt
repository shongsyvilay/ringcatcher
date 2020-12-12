package com.example.ringcatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startButton)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val beginButton = findViewById<Button>(R.id.beginButton)
        val spotText = findViewById<TextView>(R.id.spotText)
        val fireworksView = findViewById<ImageView>(R.id.fireworksView)
        val friendsView = findViewById<ImageView>(R.id.friendsView)
        val friendsText = findViewById<TextView>(R.id.friendsText)

        startButton.setOnClickListener {
            startButton.visibility = View.GONE
            spotText.visibility = View.VISIBLE
            nextButton.visibility = View.VISIBLE
        }

        nextButton.setOnClickListener {
            spotText.visibility = View.GONE
            fireworksView.visibility = View.GONE
            friendsView.visibility = View.VISIBLE
            friendsText.visibility = View.VISIBLE
            nextButton.visibility = View.GONE
            beginButton.visibility = View.VISIBLE
        }

        beginButton.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
    }
}
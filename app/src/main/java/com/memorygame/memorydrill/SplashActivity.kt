package com.memorygame.memorydrill

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by aspire on 04-07-2016.
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        val tvMasterMind = findViewById<View>(R.id.tvMasterMind) as TextView
        val face = Typeface.createFromAsset(assets, "fonts/papyrus.ttf")
        tvMasterMind.typeface = face
        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(4000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        timer.start()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
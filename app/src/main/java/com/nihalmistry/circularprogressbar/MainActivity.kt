package com.nihalmistry.circularprogressbar

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.nihalmistry.circularprogressview.CircularProgressView

class MainActivity : AppCompatActivity() {

    lateinit var myProgressView : CircularProgressView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myProgressView = findViewById(R.id.myProgressView)

        val btn_start = findViewById<Button>(R.id.btn_start)
        val txt_progress = findViewById<TextView>(R.id.txt_progress)

        btn_start.setOnClickListener { view ->
            myProgressView.progress = 50f
            val myAnimator: ValueAnimator = ValueAnimator.ofFloat(100f, 0f)
            myAnimator.addUpdateListener {
                myProgressView.progress = it.animatedValue as Float
                txt_progress.text = String.format("%.0f", it.animatedValue)
                myProgressView.alpha = (it.animatedValue as Float) / 100
                txt_progress.alpha = (it.animatedValue as Float) / 100
            }
            myAnimator.repeatMode = ValueAnimator.REVERSE
            myAnimator.repeatCount = ValueAnimator.INFINITE
            myAnimator.setDuration(6000)
            myAnimator.start()
        }
    }
}

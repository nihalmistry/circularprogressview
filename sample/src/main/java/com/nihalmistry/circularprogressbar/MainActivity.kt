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
    lateinit var btnStart : Button
    lateinit var txtProgress : TextView

    val myAnimator: ValueAnimator = ValueAnimator.ofFloat(100f, 0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myProgressView = findViewById(R.id.myProgressView)
        btnStart = findViewById<Button>(R.id.btn_start)
        txtProgress = findViewById<TextView>(R.id.txt_progress)

        myAnimator.addUpdateListener {
            myProgressView.progress = it.animatedValue as Float
            txtProgress.text = String.format("%.0f", it.animatedValue)
        }
        myAnimator.repeatMode = ValueAnimator.REVERSE
        myAnimator.repeatCount = ValueAnimator.INFINITE
        myAnimator.setDuration(6000)


        btnStart.setOnClickListener { view ->
            if ( ! myAnimator.isRunning) {
                myAnimator.start()
                btnStart.text = "Stop"
            } else {
                myAnimator.cancel()
                btnStart.text = "Start"
                myProgressView.progress = 0f
            }
        }
    }
}

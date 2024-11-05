package com.amjadxdev.stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.amjadxdev.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var isRunning = false
    private var timerSecond = 0

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        @SuppressLint("DefaultLocale")
        override fun run() {
            timerSecond++
            val hours = timerSecond / 3600
            val minutes = (timerSecond % 3600 ) / 60
            val seconds = timerSecond % 60

            val time = String.format("%02d:%20d:%02d", hours,minutes,seconds)
            viewBinding.tvTimer.text = time

            handler.postDelayed(this, 1000)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


        viewBinding.apply {
            btnStart.setOnClickListener {
                startTimer()

            }

            btnStop.setOnClickListener {
                stopTimer()
            }

            btnReset.setOnClickListener {
                resetTimer()
            }
        }




    }
    private fun startTimer() {
        if (!isRunning) {
            handler.postDelayed(runnable, 1000)
            isRunning = true

            viewBinding.apply {
                btnStart.isEnabled = false
                btnStop.isEnabled = true
                btnStop.text = "Pause"
                btnReset.isEnabled = true
            }
        }
    }


    private fun stopTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable)
            isRunning = false
        }

        viewBinding.apply {
            btnStart.isEnabled = true
            btnStart.text = "Resume"
            btnStop.isEnabled = false
            btnReset.isEnabled = true
        }
    }

    private fun resetTimer() {
        timerSecond = 0

        handler.removeCallbacks(runnable)
        isRunning = false

        viewBinding.apply {
            tvTimer.text = "00:00:00"
            btnStart.isEnabled = true
            btnStart.text = "Start"
            btnStop.isEnabled = false
            btnStop.text = "Stop"
            btnReset.isEnabled = false
        }

    }
}
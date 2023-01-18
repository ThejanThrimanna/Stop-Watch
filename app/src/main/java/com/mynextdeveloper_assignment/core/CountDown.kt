package com.mynextdeveloper_assignment.core

import android.os.CountDownTimer

abstract class CountDown(var mMillisInFuture: Long, var mInterval: Long) {

    private lateinit var countDownTimer: CountDownTimer
    private var remainingTime: Long = 0
    private var isTimerPaused: Boolean = true

    init {
        this.remainingTime = mMillisInFuture
    }

    fun start() {
        startTimer(remainingTime)
    }

    private fun startTimer(rem: Long) {
        if (isTimerPaused) {
            countDownTimer = object : CountDownTimer(rem, mInterval) {
                override fun onFinish() {
                    onTimerFinish()
                    restart()
                }

                override fun onTick(millisUntilFinished: Long) {
                    remainingTime = millisUntilFinished
                    onTimerTick(millisUntilFinished)
                }

            }.apply {
                start()
            }
            isTimerPaused = false
        }
    }

    fun pause() {
        if (!isTimerPaused) {
            countDownTimer.cancel()
        }
        isTimerPaused = true
    }

    fun restart() {
        countDownTimer.cancel()
        remainingTime = mMillisInFuture
        isTimerPaused = true
    }

    fun resume() {
        startTimer(remainingTime)
        isTimerPaused = false
    }

    fun stop() {
        countDownTimer.cancel()
        isTimerPaused = false
    }

    abstract fun onTimerTick(millisUntilFinished: Long)
    abstract fun onTimerFinish()

}
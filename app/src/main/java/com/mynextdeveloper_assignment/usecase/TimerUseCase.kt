package com.mynextdeveloper_assignment.usecase

import com.mynextdeveloper_assignment.core.State
import com.mynextdeveloper_assignment.core.Utility.TIME_COUNTDOWN
import com.mynextdeveloper_assignment.core.CountDown
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerUseCase {

    private var _timerStateFlow =
        MutableStateFlow(Triple(State.START, "60:000", 100.0f))
    val timerStateFlow: StateFlow<Triple<State, String, Float>> = _timerStateFlow

    private var totalSeconds: Long? = null

    private var countDownTimer: CountDown? = null

    private val _completed = MutableStateFlow(Any())
    val completed: StateFlow<Any> get() = _completed

    fun handleCountDownTimer(state: State) {
        when (state) {
            State.RUNNING -> {
                pauseTimer()
            }
            State.STOP -> {
                this.totalSeconds = TIME_COUNTDOWN
                stopTimer()
            }
            else -> {
                this.totalSeconds = TIME_COUNTDOWN
                startTimer()
            }
        }
    }

    private fun pauseTimer() {
        countDownTimer?.pause()
        _timerStateFlow.value =
            Triple(State.PAUSE, _timerStateFlow.value.second, _timerStateFlow.value.third)
    }

    private fun stopTimer() {
        countDownTimer?.stop()
        _timerStateFlow.value =
            Triple(State.START, "60:000", 100f)
    }

    private fun startTimer() {
        when (_timerStateFlow.value.first) {
            State.PAUSE -> {
                (countDownTimer as CountDown).resume()
            }
            State.START -> {
                countDownTimer = object : CountDown(TIME_COUNTDOWN, 1) {
                    override fun onTimerTick(millisUntilFinished: Long) {
                        handleTimerValues(millisUntilFinished)
                    }

                    override fun onTimerFinish() {
                        pauseTimer()
                        _completed.value = Any()
                        stopTimer()
                    }

                }
                (countDownTimer as CountDown).start()
            }
        }
    }

    private fun handleTimerValues(
        time: Long
    ) {

        val seconds = time.div(1000)
        val milliSeconds = time.rem(1000)
        var formattedTime = java.lang.String.format("%02d:%03d", seconds, milliSeconds)
        val displaySeconds: String = formattedTime

        val progressPercentage: Float =
            totalSeconds?.toFloat()?.let { time.div(it) } ?: 0f

        _timerStateFlow.value = Triple(State.RUNNING, displaySeconds, progressPercentage)

    }
}


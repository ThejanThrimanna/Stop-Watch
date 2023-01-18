package com.mynextdeveloper_assignment.viewmodel

import androidx.lifecycle.ViewModel
import com.mynextdeveloper_assignment.core.State
import com.mynextdeveloper_assignment.usecase.TimerUseCase
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val timerIntent = TimerUseCase()
    val timerStateFlow: StateFlow<Triple<State, String, Float>> = timerIntent.timerStateFlow
    val completed: StateFlow<Any> = timerIntent.completed
    fun toggleStart(state: State) = timerIntent.handleCountDownTimer(state)
}
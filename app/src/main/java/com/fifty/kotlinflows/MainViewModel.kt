package com.fifty.kotlinflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlowTerminalOperators()
    }

    private fun collectFlow() {
        viewModelScope.launch {
            val count = countDownFlow
                .filter { time ->
                    time % 2 == 0
                }
                .map { time ->
                    time * time
                }
                .onEach { time ->
                    println(time)
                }
                .count {
                    it % 2 == 0
                }
            println("the count is $count")
        }
    }

    private fun collectFlowTerminalOperators() {
        viewModelScope.launch {
            val reduceResult = countDownFlow
                .reduce { accumulator, value ->
                    accumulator + value
                }
            println("the count is $reduceResult")
        }
    }
}
package com.epicdevler.ad.prodigycalculator.ui.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CalculatorVM : ViewModel() {

    val uiState: MutableState<UIState> = mutableStateOf(UIState())


    fun invoke(action: CalculatorAction) {
        val activeInput = uiState.value.input
        viewModelScope.launch {
            when (action.type) {
                CalculatorAction.Type.Digit -> {
                    val newInput = if (action.value == "." && activeInput.last().toString()
                            .toIntOrNull() == null
                    ) {
                        activeInput
                    } else {
                        activeInput + action.value
                    }
                    uiState.value = uiState.value.copy(input = newInput)
                }

                CalculatorAction.Type.Operator -> {
                    if (activeInput.last().toString()
                            .toIntOrNull() == null
                    ) {
                        val inputLength = activeInput.length - 1
                        val firstHalf = activeInput.substring(0, inputLength - 2)
                        uiState.value = uiState.value.copy(input = firstHalf)
                    }
                    val newInput = "$activeInput ${action.value} "

                    uiState.value = uiState.value.copy(input = newInput)
                }

                CalculatorAction.Type.Clear -> {
                    val new = activeInput.trim().substring(0, activeInput.trim().length - 1)
                    uiState.value = uiState.value.copy(input = new.trim())
                }

                CalculatorAction.Type.ClearAll -> {
                    uiState.value = uiState.value.copy(input = "")
                }

                CalculatorAction.Type.Submit -> {

                }
            }

        }
    }

    data class UIState(
        val sum: String = "",
        val input: String = "",
        val error: String? = null,
        val message: String? = null,
    )

}
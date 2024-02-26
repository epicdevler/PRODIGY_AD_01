package com.epicdevler.ad.prodigycalculator.ui.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epicdevler.ad.prodigycalculator.util.CalculatorAction
import kotlinx.coroutines.launch
import org.mariuszgromada.math.mxparser.Expression

class CalculatorVM : ViewModel() {

    val uiState: MutableState<UIState> = mutableStateOf(UIState())


    fun invoke(action: CalculatorAction) {
        val activeInput = uiState.value.input
        var newInput: String
        viewModelScope.launch {
            when (action.type) {
                CalculatorAction.Type.Digit -> {
                    newInput = if (action.value == "." && activeInput.last().toString()
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
                        newInput = activeInput.substring(0, inputLength - 2)
                        
                        uiState.value = uiState.value.copy(input = newInput)
                    }
                    newInput = "$activeInput ${action.value} "

                    uiState.value = uiState.value.copy(input = newInput)
                }

                CalculatorAction.Type.Clear -> {
                    newInput = activeInput.trim().substring(0, activeInput.trim().length - 1).trim()
                    uiState.value = uiState.value.copy(input = newInput)
                }

                CalculatorAction.Type.ClearAll -> {
                    uiState.value = uiState.value.copy(input = "", sum = "")
                }

                CalculatorAction.Type.Submit -> {

                    val sum = Expression(uiState.value.input.trim()).calculate()

                    if(sum.isNaN()){
                        uiState.value = uiState.value.copy(error = "Invalid/Incomplete input, could not perform operation")
                    }else{
                        uiState.value = uiState.value.copy(sum = sum.toString())
                    }
                }
            }
//            uiState.value  = uiState.value.copy(input = newInput)

        }
    }

    fun clearError() {
        uiState.value = uiState.value.copy(error = null)
    }

    data class UIState(
        val sum: String = "",
        val input: String = "",
        val error: String? = null,
        val message: String? = null,
    )

}
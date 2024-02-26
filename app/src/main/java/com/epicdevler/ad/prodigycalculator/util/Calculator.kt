package com.epicdevler.ad.prodigycalculator.util

object Calculator {
    object Constant{
        const val BACK_LABEL = "\u232B"
    }
    val buttons = listOf(
        listOf(
            CalculatorAction(value = "C", type = CalculatorAction.Type.ClearAll),
            CalculatorAction(value = Constant.BACK_LABEL, type = CalculatorAction.Type.Clear),
            CalculatorAction(value = "^", type = CalculatorAction.Type.Operator),
            CalculatorAction(value = "÷", type = CalculatorAction.Type.Operator)
        ),
        listOf(
            CalculatorAction(value = "1", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "2", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "3", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "×", type = CalculatorAction.Type.Operator),
        ),
        listOf(
            CalculatorAction(value = "4", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "5", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "6", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "+", type = CalculatorAction.Type.Operator),
        ),
        listOf(
            CalculatorAction(value = "7", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "8", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "9", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "-", type = CalculatorAction.Type.Operator),
        ),
        listOf(
            CalculatorAction(value = ".", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "0", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "00", type = CalculatorAction.Type.Digit),
            CalculatorAction(value = "=", type = CalculatorAction.Type.Submit),
        ),
    )
}
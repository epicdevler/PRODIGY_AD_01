package com.epicdevler.ad.prodigycalculator.util

data class CalculatorAction(
    val value: String,
    val type: Type
) {
    enum class Type {
        Digit, Operator, Clear, ClearAll, Submit
    }
}
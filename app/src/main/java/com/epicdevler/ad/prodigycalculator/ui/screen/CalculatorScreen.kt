package com.epicdevler.ad.prodigycalculator.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

data class CalculatorAction(
    val value: String,
    val type: Type
) {
    enum class Type {
        Digit, Operator, Clear, ClearAll, Submit
    }
}

@Composable
fun Calculator() {
    val vm: CalculatorVM = viewModel()

    val uiState = vm.uiState.value

    val operatorsFirst = listOf(
        CalculatorAction(
            value = "C",
            type = CalculatorAction.Type.Clear
        ),
        CalculatorAction(
            value = "+/-",
            type = CalculatorAction.Type.Operator
        ),
        CalculatorAction(
            value = "%",
            type = CalculatorAction.Type.Operator
        ),
        CalculatorAction(
            value = "÷",
            type = CalculatorAction.Type.Operator
        )
    )

    val digits = listOf(
        operatorsFirst,
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

    Column(
        modifier = Modifier
            .background(Color(0xFFD9E1EA))
            .fillMaxSize(),
    ) {
        Text(
            text = "01 Calculator",
            style = typography.titleLarge,
            color = Color(0x80373737),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),

            )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier

                .weight(1f)
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = uiState.input.ifEmpty { "0" },
                style = typography.titleMedium,
                color = Color(0x80373737),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),

                )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "=",
                    style = typography.titleLarge,
                    color = Color(0x80373737),
                    textAlign = TextAlign.End,
                )
                Text(
                    text = uiState.sum,
                    style = typography.titleLarge,
                    color = Color(0x80373737),
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Column(
            modifier = Modifier
                .background(Color(0xFFFEFEFE))
                .fillMaxWidth()
        ) {
            digits.forEach {
                Row {
                    it.forEach { action ->
                        Btn(
                            label = action.value,
                            bg = when (action.type) {
                                CalculatorAction.Type.Submit -> colorScheme.primary
                                else -> Color.Transparent
                            },
                            textColor = when (action.type) {
                                CalculatorAction.Type.Submit -> colorScheme.onPrimary
                                CalculatorAction.Type.Operator -> colorScheme.primary
                                else -> colorScheme.onBackground
                            },
                            modifier = Modifier.fillMaxSize(),
                            onClick = {
                                vm.invoke(action)
                            },
                            onLongClick = {
                                if (action.type == CalculatorAction.Type.Clear) {
                                    vm.invoke(action.copy(type = CalculatorAction.Type.ClearAll))
                                }
                            }
                        )


                    }

                }
            }

        }
    }
}

@Composable
fun RowScope.Btn(
    bg: Color = Color(0x4DFFFFFF),
    textColor: Color = colorScheme.onBackground,
    label: String,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val shape = shapes.extraLarge
    Box(
        modifier = Modifier
            .padding(8.dp)
            .weight(1f)
            .aspectRatio(1f, true)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(shape = shape)
                .background(color = bg, shape = shape)
                .clickable(
                    onClick = onClick,
                    onClickLabel = label
                )
                .padding(16.dp)
                .then(modifier)
        ) {
            Text(
                text = label,
                style = typography.titleMedium,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
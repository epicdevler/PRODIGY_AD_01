package com.epicdevler.ad.prodigycalculator.ui.screen

import android.widget.Toast
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epicdevler.ad.prodigycalculator.util.Calculator
import com.epicdevler.ad.prodigycalculator.util.CalculatorAction
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculator() {
    val context = LocalContext.current
    val vm: CalculatorVM = viewModel()

    val uiState = vm.uiState.value

    LaunchedEffect(key1 = uiState.error) {
        if (uiState.error != null) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
            delay(1000)
            vm.clearError()
        }
    }

    Column(
        modifier = Modifier
            .background(Color(0xFFD9E1EA))
            .fillMaxSize(),
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            ),
            title = {
                Text(
                    text = "Calculator",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier

                .weight(1f)
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = uiState.input.ifEmpty { "00" },
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
                    style = typography.displaySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0x80373737),
                    textAlign = TextAlign.End,
                )
                Text(
                    text = uiState.sum.ifEmpty { "00" },
                    style = typography.displaySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0x80373737),
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Buttons(
            onPress = { action ->
                vm.invoke(action)
            }
        )
    }
}

@Composable
fun Buttons(
    onPress: (action: CalculatorAction) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color(0xFFFEFEFE))
            .fillMaxWidth()
    ) {
        Calculator.buttons.forEach {
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
                            onPress(action)
                        }
                    )


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
                .indication(indication = LocalIndication.current, interactionSource = remember {
                    MutableInteractionSource()
                })
                .clip(shape = shape)
                .background(color = bg, shape = shape)
                .clickable {
                    onClick()
                }
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
package com.jphill.mbtadepatureboard.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

data class NearbyStop(
    val name: String,
    val predictions: List<NearbyStopPrediction>,
)

data class NearbyStopPrediction(
    val destination: String,
    val timeToArrival: String,
    val backgroundColor: String,
    val textColor: String,
)

private val ItemShape = RoundedCornerShape(8.dp)

@Composable
fun NearbyStopItem(
    stop: NearbyStop,
    onStopClick: (NearbyStop) -> Unit,
) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = ItemShape,
            )
            .clip(ItemShape)
            .clickable { onStopClick(stop) },
    ) {
        Text(
            text = stop.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp),
        )
        stop.predictions.take(4).forEach { prediction ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Color(prediction.backgroundColor.getColorInt()))
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = prediction.destination,
                    color = Color(prediction.textColor.getColorInt()),
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = prediction.timeToArrival,
                    color = Color(prediction.textColor.getColorInt()),
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Composable
fun NearbyStopGrid(
    stops: List<NearbyStop>,
    onStopClick: (NearbyStop) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(stops) { stop ->
            NearbyStopItem(stop, onStopClick)
        }
    }
}

@Preview
@Composable
fun NearbyStopPreview() {
    MaterialTheme {
        NearbyStopGrid(
            stops = listOf(
                exampleStop1,
                exampleStop2,
                exampleStop1,
                exampleStop1,
            ),
            onStopClick = {},
        )
    }
}

private val exampleStop1 = NearbyStop(
    name = "North Station",
    predictions = listOf(
        NearbyStopPrediction(
            destination = "Union Sq.",
            timeToArrival = "2 min",
            backgroundColor = "#00843D",
            textColor = "FFFFFF",
        ),
        NearbyStopPrediction(
            destination = "Riverside",
            timeToArrival = "3 min",
            backgroundColor = "#00843D",
            textColor = "FFFFFF",
        ),
        NearbyStopPrediction(
            destination = "Oak Grove",
            timeToArrival = "1 min",
            backgroundColor = "#ED8B00",
            textColor = "FFFFFF",
        ),
        NearbyStopPrediction(
            destination = "Forest Hills",
            timeToArrival = "4 min",
            backgroundColor = "#ED8B00",
            textColor = "FFFFFF",
        ),
    ),
)

private val exampleStop2 = NearbyStop(
    name = "North Station",
    predictions = listOf(
        NearbyStopPrediction(
            destination = "Union Sq.",
            timeToArrival = "2 min",
            backgroundColor = "#00843D",
            textColor = "FFFFFF",
        ),
        NearbyStopPrediction(
            destination = "Riverside",
            timeToArrival = "3 min",
            backgroundColor = "#00843D",
            textColor = "FFFFFF",
        ),
    ),
)

private fun String.getColorInt(): Int {
    val hexCode = if (!startsWith("#")) {
        "#$this"
    } else {
        this
    }
    return hexCode.toColorInt()
}
package com.example.a30days

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a30days.model.HistoricPlace
import com.example.a30days.model.HistoricPlaceDataSource
import com.example.a30days.ui.theme.AppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HistoricPlaceScreen(
    historicPlaces: List<HistoricPlace>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(historicPlaces) { index, place ->
                HistoricPlaceListItem(
                    place = place,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = StiffnessVeryLow,
                                    dampingRatio = DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) }
                            )
                        )
                )
            }
        }
    }
}



@Composable
fun HistoricPlaceListItem(
    place: HistoricPlace,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) } // Estado para controlar si la descripción está expandida

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .clickable { isExpanded = !isExpanded } // Cambiar el estado al hacer clic
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Mostrar "Day X" al principio
            Text(
                text = stringResource(place.dayRes),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar el nombre del lugar
            Text(
                text = stringResource(place.nameRes),
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Imagen al final
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface) // Fondo opcional
            ) {
                Image(
                    painter = painterResource(place.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar la descripción con animación de deslizamiento
            AnimatedVisibility(
                visible = isExpanded,
                enter = slideInVertically(
                    animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
                ),
                exit = fadeOut()
            ) {
                Text(
                    text = stringResource(place.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HistoricPlacePreview() {
    val place = HistoricPlace(
        R.string.day1,
        R.string.place1,
        R.string.description1,
        R.drawable.image1
    )
    AppTheme {
        HistoricPlaceListItem(place = place)
    }
}

@Preview("Historic Places List")
@Composable
fun HistoricPlacesListPreview() {
    AppTheme(darkTheme = false) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            HistoricPlaceScreen(historicPlaces = HistoricPlaceDataSource.places)
        }
    }
}

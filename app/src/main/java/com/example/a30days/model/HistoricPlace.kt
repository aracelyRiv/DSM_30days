package com.example.a30days.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class HistoricPlace(
    @StringRes val dayRes: Int, // Recurso para el número del día
    @StringRes val nameRes: Int, // Recurso para el nombre del lugar
    @StringRes val descriptionRes: Int, // Recurso para la descripción
    @DrawableRes val imageRes: Int // Imagen
)

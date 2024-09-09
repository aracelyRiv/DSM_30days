package com.example.a30days

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.a30days.model.HistoricPlace
import com.example.a30days.model.HistoricPlaceDataSource
import com.example.a30days.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HistoricPlacesApp()
                }
            }
        }
    }

    @Composable
    fun HistoricPlacesApp() {
        Scaffold(
            topBar = {
                TopAppBar()
            },
            content = { paddingValues ->
                HistoricPlaceScreen(
                    historicPlaces = HistoricPlaceDataSource.places,
                    contentPadding = paddingValues
                )
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(modifier: Modifier = Modifier) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            },
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewHistoricPlacesApp() {
        AppTheme {
            HistoricPlacesApp()
        }
    }
}

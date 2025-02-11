package com.work.challengeyapeapp.features.mapScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.work.challengeyapeapp.core.mapsUtils.Utils
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.work.challengeyapeapp.R
import com.work.challengeyapeapp.core.common.GlobalConstants

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter",
    "UnusedMaterial3ScaffoldPaddingParameter",
    "ResourceAsColor"
)
@Composable
internal fun MapScreen(
    mark: String,
    navController: NavController
) {
    val country = remember { Utils().getMark(mark) }
    val marker = remember { LatLng(country.latitudeMark, country.altitudeMark) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(marker, GlobalConstants.MAP_ZOOM)
    }
    Scaffold(
        topBar = {
            MapScreenTopBar(navController)
        }
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(compassEnabled = true)
        ) {
            MapMarker(marker, mark, country.latitudeMark, country.altitudeMark)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.mapScreen_title),
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
        }
    )
}

@Composable
fun MapMarker(marker: LatLng, mark: String, latitude: Double, altitude: Double) {
    val markerState = rememberMarkerState(position = marker)
    LaunchedEffect(markerState) {
        markerState.showInfoWindow()
    }
    Marker(
        state = markerState,
        title = mark,
        snippet = stringResource(R.string.mapScreen_mark) + " $latitude, $altitude",
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
    )
}

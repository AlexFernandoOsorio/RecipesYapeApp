package com.work.challengeyapeapp.features.homeScreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.work.challengeyapeapp.R
import com.work.challengeyapeapp.core.common.GlobalConstants
import com.work.challengeyapeapp.features.navigation.AppScreens

@Composable
internal fun SplashScreen(
    navController: NavController
) {

    val scaleAnimation = remember {
        Animatable(GlobalConstants.INITIAL_VALUE)
    }

    AnimationSplashContent(
        scaleAnimation = scaleAnimation,
        navController = navController,
        durationMillisAnimation = GlobalConstants.DURATION_ANIMATION,
        delayScreen = GlobalConstants.SCREEN_DELAY
    )

    DesignSplashScreen(
        imagePainter = painterResource(id = R.drawable.yape_logo),
        scaleAnimation = scaleAnimation
    )
}

@Composable
fun AnimationSplashContent(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    navController: NavController,
    durationMillisAnimation: Int,
    delayScreen: Long
) {
    LaunchedEffect(key1 = true) {
        scaleAnimation.animateTo(
            targetValue = GlobalConstants.TARGET_VALUE,
            animationSpec = tween(
                durationMillis = durationMillisAnimation,
                easing = {
                    OvershootInterpolator(GlobalConstants.TENSION).getInterpolation(it)
                }
            )
        )
        delay(timeMillis = delayScreen)
        navController.popBackStack()
        navController.navigate(AppScreens.HomeScreen.route) {
        }
    }
}

@Composable
fun DesignSplashScreen(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    scaleAnimation: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = imagePainter,
                contentDescription = stringResource(id = R.string.splashScreen_label),
                modifier = modifier
                    .size(dimensionResource(id = R.dimen.size_1000))
                    .scale(scale = scaleAnimation.value)
                    .clip(CircleShape),
            )
        }
    }
}

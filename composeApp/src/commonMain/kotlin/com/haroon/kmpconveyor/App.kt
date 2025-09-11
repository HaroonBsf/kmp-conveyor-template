package com.haroon.kmpconveyor

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jetbrains.compose.ui.tooling.preview.Preview

private object AppColors {
    val BACKGROUND_GRADIENT = listOf(
        Color(0xFF1a1a2e),
        Color(0xFF16213e),
        Color(0xFF0f3460)
    )
    val PRIMARY_TEXT = Color.White
    val SECONDARY_TEXT = Color(0xFF00D4FF)
    val DOT_COLOR = Color.Cyan.copy(alpha = 0.9f)
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var greeting by remember { mutableStateOf("Loading...") }
        
        LaunchedEffect(Unit) {
            greeting = withContext(Dispatchers.IO) {
                delay(500)
                Greeting().greet()
            }
        }
        
        AppBackground {
            HydraulicConveyorAnimation()
            OSNameDisplay(greeting = greeting)
        }
    }
}

@Composable
private fun AppBackground(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = AppColors.BACKGROUND_GRADIENT)
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun OSNameDisplay(greeting: String) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize().padding(bottom = 32.dp)
    ) {
        Text(
            text = greeting,
            color = AppColors.PRIMARY_TEXT,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun HydraulicConveyorAnimation() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            MainTitle()
            Spacer(modifier = Modifier.height(8.dp))
            Subtitle()
            Spacer(modifier = Modifier.height(30.dp))
            AnimatedDots()
        }
    }
}

@Composable
private fun MainTitle() {
    Text(
        text = "HYDRAULIC",
        color = AppColors.PRIMARY_TEXT,
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        letterSpacing = 4.sp
    )
}

@Composable
private fun Subtitle() {
    Text(
        text = "CONVEYOR",
        color = AppColors.SECONDARY_TEXT,
        fontSize = 36.sp,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center,
        letterSpacing = 6.sp
    )
}

@Composable
private fun AnimatedDots() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(3) { index ->
            AnimatedDot(index = index)
        }
    }
}

@Composable
private fun AnimatedDot(index: Int) {
    val infiniteTransition = rememberInfiniteTransition(label = "dotPulse")
    
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, delayMillis = index * 200, easing = { it * it * (3 - 2 * it) }),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotPulse"
    )
    
    Box(
        modifier = Modifier
            .size(8.dp)
            .scale(pulseScale)
            .background(
                color = AppColors.DOT_COLOR,
                shape = CircleShape
            )
    )
}
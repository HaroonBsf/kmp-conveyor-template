package com.haroon.kmpconveyor

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.inputStream

fun main() = application {
    val appIcon = rememberAppIcon()

    Window(
        onCloseRequest = ::exitApplication,
        icon = appIcon,
        title = "KMP Conveyor Template"
    ) {
        App()
    }
}

fun rememberAppIcon(): BitmapPainter? {
    return System.getProperty("app.dir")
        ?.let { Paths.get(it, "icon-512.png") }
        ?.takeIf { it.exists() }
        ?.inputStream()
        ?.buffered()
        ?.use { BitmapPainter(loadImageBitmap(it)) }
}

package com.haroon.kmpconveyor

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp-conveyor-template",
    ) {
        App()
    }
}
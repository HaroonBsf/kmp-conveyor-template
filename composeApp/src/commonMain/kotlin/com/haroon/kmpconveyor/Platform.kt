package com.haroon.kmpconveyor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
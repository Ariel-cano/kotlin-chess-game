package com.ariel.chess

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
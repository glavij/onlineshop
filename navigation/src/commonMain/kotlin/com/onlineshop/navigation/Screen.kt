package com.onlineshop.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{
    @Serializable
    data object Auth:Screen()
}
package com.hamid.onlineshop

import androidx.compose.ui.window.ComposeUIViewController
import com.hamid.di.initializeKoin

fun MainViewController() = ComposeUIViewController (
    configure = { initializeKoin() }
){ App() }
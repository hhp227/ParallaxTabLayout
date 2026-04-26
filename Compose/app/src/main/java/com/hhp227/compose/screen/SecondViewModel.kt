package com.hhp227.compose.screen

import androidx.lifecycle.ViewModel

class SecondViewModel : ViewModel() {
    val items: List<String> = List(20) { index -> "item${index + 1}" }
}

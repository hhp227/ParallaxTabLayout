package com.hhp227.compose.screen

import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {
    val items: List<String> = List(20) { index -> "item${index + 1}" }
}

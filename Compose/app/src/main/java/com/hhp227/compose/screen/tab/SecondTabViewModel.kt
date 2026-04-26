package com.hhp227.compose.screen.tab

import androidx.lifecycle.ViewModel

class SecondTabViewModel : ViewModel() {
    val items: List<String> = List(20) { index -> "item${index + 1}" }
}

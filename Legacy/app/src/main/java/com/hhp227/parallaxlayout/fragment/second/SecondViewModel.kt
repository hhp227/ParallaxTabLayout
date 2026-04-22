package com.hhp227.parallaxlayout.fragment.second

import androidx.lifecycle.ViewModel

class SecondViewModel : ViewModel() {
    val items: List<String> = List(20) { index -> "item${index + 1}" }
}

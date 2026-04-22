package com.hhp227.parallaxlayout.fragment.first

import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {
    val items: List<String> = List(20) { index -> "item${index + 1}" }
}

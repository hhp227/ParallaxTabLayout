package com.hhp227.compose.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hhp227.compose.ui.AppToolbar
import com.hhp227.compose.ui.NavigationIcon

@Composable
fun DetailScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = "Detail",
                navigationIcon = NavigationIcon.Back,
                onNavigationClick = onBack
            )
        }
    ) { innerPadding ->
        Text(
            text = "Hello blank fragment",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

package com.hhp227.compose.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hhp227.compose.data.sampleItems
import com.hhp227.compose.ui.CollapsingListScaffold
import com.hhp227.compose.ui.RefreshableLazyColumn

@Composable
fun ParallaxTabScreen(title: String, onBack: () -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val items = sampleItems()

    Box(modifier = Modifier.fillMaxSize()) {
        CollapsingListScaffold(
            title = title,
            navigationText = "Back",
            onNavigationClick = onBack,
            showTabs = true,
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        ) { listState, headerHeight, isAppBarExpanded ->
            RefreshableLazyColumn(
                state = listState,
                contentPadding = PaddingValues(top = headerHeight),
                modifier = Modifier.fillMaxSize(),
                enabled = selectedTab == 0 && isAppBarExpanded
            ) {
                items(items) { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 18.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Divider()
                }
            }
        }
        if (selectedTab == 0) {
            ExtendedFloatingActionButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Text("Action")
            }
        }
    }
}

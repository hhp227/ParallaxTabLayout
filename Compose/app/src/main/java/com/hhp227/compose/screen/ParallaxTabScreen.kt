package com.hhp227.compose.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hhp227.compose.screen.tab.FirstTabScreen
import com.hhp227.compose.screen.tab.SecondTabScreen
import com.hhp227.compose.screen.tab.isFirstItemVisible
import com.hhp227.compose.ui.CollapsingListScaffold
import com.hhp227.compose.ui.NavigationIcon

@Composable
fun ParallaxTabScreen(title: String, onBack: () -> Unit) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val firstTabListState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    val secondTabListState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    var collapseOffsetPx by rememberSaveable { mutableStateOf(0f) }
    val selectedListState = if (selectedTab == 0) firstTabListState else secondTabListState

    Box(modifier = Modifier.fillMaxSize()) {
        CollapsingListScaffold(
            title = title,
            navigationIcon = NavigationIcon.Back,
            onNavigationClick = onBack,
            showTabs = true,
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            listState = selectedListState,
            collapseOffsetPx = collapseOffsetPx,
            onCollapseOffsetPxChange = { collapseOffsetPx = it }
        ) { listState, headerHeight, appBarState ->
            LaunchedEffect(selectedTab, listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
                if (!listState.isFirstItemVisible()) {
                    appBarState.setExpanded(false)
                }
            }
            when (selectedTab) {
                0 -> FirstTabScreen(
                    listState = firstTabListState,
                    headerHeight = headerHeight,
                    isRefreshEnabled = appBarState.isExpanded
                )
                1 -> SecondTabScreen(
                    listState = secondTabListState,
                    headerHeight = headerHeight
                )
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

package com.hhp227.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hhp227.compose.R
import kotlin.math.max

class CollapsingAppBarState internal constructor(
    private val collapseOffsetPxProvider: () -> Float,
    private val maxCollapsePxProvider: () -> Float,
    private val setCollapseOffsetPx: (Float) -> Unit
) {
    val isExpanded: Boolean
        get() = collapseOffsetPxProvider() <= 0.5f

    fun setExpanded(expanded: Boolean) {
        setCollapseOffsetPx(if (expanded) 0f else maxCollapsePxProvider())
    }
}

@Composable
fun CollapsingListScaffold(
    title: String,
    navigationIcon: NavigationIcon,
    onNavigationClick: () -> Unit,
    showTabs: Boolean,
    selectedTab: Int = 0,
    onTabSelected: (Int) -> Unit = {},
    listState: LazyListState = rememberLazyListState(),
    content: @Composable (LazyListState, Dp, CollapsingAppBarState) -> Unit
) {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val toolbarHeight = 56.dp
    val tabHeight = if (showTabs) 48.dp else 0.dp
    val expandedHeight = statusBarHeight + 256.dp
    val collapsedHeight = statusBarHeight + toolbarHeight + tabHeight
    val density = LocalDensity.current
    val maxCollapsePx = with(density) {
        (expandedHeight - collapsedHeight).toPx().coerceAtLeast(0f)
    }
    val collapseOffsetPx = remember(maxCollapsePx) { mutableFloatStateOf(0f) }
    val appBarState = remember(maxCollapsePx) {
        CollapsingAppBarState(
            collapseOffsetPxProvider = { collapseOffsetPx.floatValue },
            maxCollapsePxProvider = { maxCollapsePx },
            setCollapseOffsetPx = { collapseOffsetPx.floatValue = it.coerceIn(0f, maxCollapsePx) }
        )
    }
    val nestedScrollConnection = remember(maxCollapsePx) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y >= 0f) return Offset.Zero

                val previousOffset = collapseOffsetPx.floatValue
                val newOffset = (previousOffset - available.y).coerceIn(0f, maxCollapsePx)
                collapseOffsetPx.floatValue = newOffset
                return Offset(x = 0f, y = previousOffset - newOffset)
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                if (available.y <= 0f) return Offset.Zero

                val previousOffset = collapseOffsetPx.floatValue
                val newOffset = (previousOffset - available.y).coerceIn(0f, maxCollapsePx)
                collapseOffsetPx.floatValue = newOffset
                return Offset(x = 0f, y = previousOffset - newOffset)
            }
        }
    }
    val collapseFraction = if (maxCollapsePx == 0f) 1f else collapseOffsetPx.floatValue / maxCollapsePx
    val headerHeight = with(density) {
        (expandedHeight.toPx() - collapseOffsetPx.floatValue).toDp()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        content(listState, headerHeight, appBarState)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(R.drawable.header),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(max(0f, 1f - collapseFraction))
            )
            Column(modifier = Modifier.fillMaxSize()) {
                AppToolbar(
                    title = title,
                    navigationIcon = navigationIcon,
                    onNavigationClick = onNavigationClick,
                    transparent = true
                )
                Spacer(modifier = Modifier.weight(1f))
                if (showTabs) {
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ) {
                        listOf("First", "Second").forEachIndexed { index, label ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { onTabSelected(index) },
                                text = { Text(label) }
                            )
                        }
                    }
                }
            }
        }
    }
}

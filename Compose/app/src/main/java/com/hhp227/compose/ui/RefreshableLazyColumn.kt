package com.hhp227.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.delay

@Composable
fun RefreshableLazyColumn(
    state: LazyListState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    var pullDistance by remember { mutableFloatStateOf(0f) }
    var refreshing by remember { mutableStateOf(false) }
    val nestedScrollConnection = remember(enabled, refreshing, state) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (!enabled || refreshing || available.y >= 0f || pullDistance <= 0f) {
                    return Offset.Zero
                }

                val previousPull = pullDistance
                pullDistance = (pullDistance + available.y).coerceAtLeast(0f)
                return Offset(x = 0f, y = pullDistance - previousPull)
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                val atTop = state.firstVisibleItemIndex == 0 && state.firstVisibleItemScrollOffset == 0
                if (!enabled || refreshing || !atTop || available.y <= 0f) {
                    return Offset.Zero
                }

                pullDistance += available.y
                return Offset(x = 0f, y = available.y)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                refreshing = pullDistance > 90f
                if (!refreshing) pullDistance = 0f
                return Velocity.Zero
            }
        }
    }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(1_000L)
            refreshing = false
            pullDistance = 0f
        }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = state,
            contentPadding = contentPadding,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection),
            content = content
        )
        if (enabled && (refreshing || pullDistance > 0f)) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = contentPadding.calculateTopPadding() + 24.dp)
                    .size(32.dp)
            )
        }
    }
}

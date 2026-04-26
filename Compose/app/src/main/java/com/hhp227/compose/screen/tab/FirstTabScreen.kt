package com.hhp227.compose.screen.tab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hhp227.compose.ui.RefreshableLazyColumn

@Composable
fun FirstTabScreen(
    listState: LazyListState,
    headerHeight: Dp,
    isRefreshEnabled: Boolean,
    modifier: Modifier = Modifier,
    viewModel: FirstTabViewModel = viewModel()
) {
    RefreshableLazyColumn(
        state = listState,
        contentPadding = PaddingValues(top = headerHeight),
        modifier = modifier.fillMaxSize(),
        enabled = isRefreshEnabled
    ) {
        items(viewModel.items) { item ->
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

fun LazyListState.isFirstItemVisible(): Boolean =
    firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset == 0

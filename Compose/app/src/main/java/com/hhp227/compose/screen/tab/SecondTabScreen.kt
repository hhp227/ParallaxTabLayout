package com.hhp227.compose.screen.tab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun SecondTabScreen(
    listState: LazyListState,
    headerHeight: Dp,
    modifier: Modifier = Modifier,
    viewModel: SecondTabViewModel = viewModel()
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(top = headerHeight),
        modifier = modifier.fillMaxSize()
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

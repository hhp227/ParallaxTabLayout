package com.hhp227.compose.screen

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hhp227.compose.data.sampleItems
import com.hhp227.compose.ui.CollapsingListScaffold
import com.hhp227.compose.ui.DrawerScaffold
import com.hhp227.compose.ui.RefreshableLazyColumn

@Composable
fun FirstScreen(
    selectedRoute: String,
    onNavigateDrawer: (String) -> Unit,
    onOpenDetail: () -> Unit
) {
    DrawerScaffold(
        selectedRoute = selectedRoute,
        onNavigate = onNavigateDrawer
    ) { openDrawer ->
        val items = sampleItems()
        Box(modifier = Modifier.fillMaxSize()) {
            CollapsingListScaffold(
                title = "FirstFragment",
                navigationText = "Menu",
                onNavigationClick = openDrawer,
                showTabs = false
            ) { listState, headerHeight, isAppBarExpanded ->
                RefreshableLazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(top = headerHeight),
                    modifier = Modifier.fillMaxSize(),
                    enabled = isAppBarExpanded
                ) {
                    items(items) { item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenDetail() }
                                .padding(horizontal = 16.dp, vertical = 18.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Divider()
                    }
                }
            }
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

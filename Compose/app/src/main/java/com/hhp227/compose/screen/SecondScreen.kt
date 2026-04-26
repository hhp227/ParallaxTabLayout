package com.hhp227.compose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hhp227.compose.R
import com.hhp227.compose.ui.AppToolbar
import com.hhp227.compose.ui.DrawerScaffold
import com.hhp227.compose.ui.NavigationIcon

@Composable
fun SecondScreen(
    selectedRoute: String,
    onNavigateDrawer: (String) -> Unit,
    onOpenParallaxTab: (String) -> Unit,
    viewModel: SecondViewModel = viewModel()
) {
    DrawerScaffold(
        selectedRoute = selectedRoute,
        onNavigate = onNavigateDrawer
    ) { openDrawer ->
        Scaffold(
            topBar = {
                AppToolbar(
                    title = "SecondFragment",
                    navigationIcon = NavigationIcon.Menu,
                    onNavigationClick = openDrawer
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                BottomGroupNavigation()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(start = 14.dp, end = 12.dp, top = 8.dp, bottom = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(viewModel.items) { item ->
                        GroupGridItem(item = item, onClick = { onOpenParallaxTab(item) })
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomGroupNavigation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf("그룹찾기", "가입신청중 그룹", "그룹만들기").forEach { label ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun GroupGridItem(item: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = item,
                color = Color(0xFF4C4C4C),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
            )
        }
    }
}

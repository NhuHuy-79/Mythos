package com.nhuhuy.mythos.creatures.presentation.home

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nhuhuy.mythos.R
import com.nhuhuy.mythos.core.ui.component.ErrorSection
import com.nhuhuy.mythos.core.ui.component.LoadingSection
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.presentation.home.component.CreatureItem
import com.nhuhuy.mythos.creatures.presentation.home.component.MythosBottomSheet
import com.nhuhuy.mythos.creatures.presentation.home.component.MythosSearchBar
import com.nhuhuy.mythos.creatures.presentation.home.component.NetworkStateHandler
import com.nhuhuy.mythos.creatures.presentation.home.component.TabScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    onDetail: (Int) -> Unit,
    onWiki: () -> Unit,
    viewModel: HomeViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isShowBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.observeCreatureList()
    }

    Log.d("List Screen", "$state")
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                actions = {
                    MythosSearchBar(
                        onSearchCancel = {
                            viewModel.updateSearchQuery("")
                            viewModel.changeSearchStatus(false)
                            focusManager.clearFocus()
                        },
                        visible = state.isSearching,
                        modifier = Modifier,
                        query = state.query,
                        onSearch = viewModel::updateSearchQuery,
                        focusRequester = focusRequester
                    )

                    IconButton(
                        onClick = { viewModel.changeSearchStatus(true) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "search",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    IconButton(
                        onClick = { isShowBottomSheet = true }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "more",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                },
            )
        },
    )
    { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {

            if (isShowBottomSheet) {
                MythosBottomSheet(
                    onDismiss = { isShowBottomSheet = false },
                    onGoWiki = {
                        isShowBottomSheet = false
                        onWiki()
                    },
                    onAboutUs = {
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/NhuHuy-79")
                        ).also {
                            context.startActivity(it)
                        }

                        isShowBottomSheet = false
                    }
                )
            }

            NetworkStateHandler(
                modifier = Modifier.fillMaxSize(),
                resource = state.result,
                onLoading = {
                    LoadingSection()
                },
                onSuccess = { creatures ->
                    TabScreen(
                        query = state.query,
                        all = creatures,
                        onDetailClick = onDetail
                    )
                },
                onFailure = {
                    ErrorSection(
                        onRetry = viewModel::onRetry
                    )
                }
            )
        }
    }
}

@Composable
fun SuccessSection(
    creatures: List<Creature>,
    onDetailClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!creatures.isEmpty()) {
            items(creatures, key = { it.id }) { item ->
                CreatureItem(
                    creature = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                        .clickable {
                            onDetailClick(item.id)
                        }
                        .animateItem()
                )
            }
        } else {

            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_book),
                        contentDescription = "empty",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "No creature found!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }

}


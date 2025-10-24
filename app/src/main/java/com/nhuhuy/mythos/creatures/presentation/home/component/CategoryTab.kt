package com.nhuhuy.mythos.creatures.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nhuhuy.mythos.core.utils.filterCategory
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.presentation.home.SuccessSection
import kotlinx.coroutines.launch

@Composable
fun TabScreen(
    all: List<Creature>,
    onDetailClick: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { CategoryTab.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val categories = remember { CategoryTab.entries }

    val outerGods = all.filterCategory("outer god")
    val greatOldOnes = all.filterCategory("great old one")
    val lessOldOnes = all.filterCategory("lesser old one")


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 0.dp,
            divider = {
                HorizontalDivider(thickness = 2.dp)
            }
        ) {
            categories.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(tab.ordinal)
                        }

                    },
                    text = {
                        Text(
                            text = tab.label,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pager ->

            SuccessSection(
                modifier = Modifier.fillMaxSize(),
                creatures = when (pager) {
                    0 -> all
                    1 -> outerGods
                    2 -> greatOldOnes
                    3 -> lessOldOnes
                    else -> emptyList()
                },
                onDetailClick = onDetailClick
            )

        }
    }
}

enum class CategoryTab(
    val label: String,
) {
    ALL("All"),
    OUTER_GOD("Outer Gods"),
    GREAT_ONE("Great Old Ones"),
    LESS_ONE("Less Old Ones"),
}
package com.nhuhuy.mythos.creatures.presentation.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nhuhuy.mythos.R
import com.nhuhuy.mythos.core.ui.component.LoadingSection
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun DetailImageSlider(
    images: List<String>,
    modifier: Modifier,
    onImageClick: (String) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { images.size }
    )
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > 0) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                (pagerState.currentPage - 1).coerceAtLeast(0)
                            )
                        }
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                (pagerState.currentPage + 1).coerceAtLeast(images.lastIndex)
                            )
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (images.isEmpty()) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.img_placholder),
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No image found.",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 60.dp),
            pageSpacing = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) { pager ->
            val pageOffset = (pagerState.currentPage - pager) + pagerState.currentPageOffsetFraction
            val scale = 1f - (0.2f * abs(pageOffset))
            var isLoading by remember { mutableStateOf(true) }
            var isError by remember { mutableStateOf(false) }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(350.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .clickable {
                        onImageClick(images[pager])
                    }
            ) {
                AsyncImage(
                    model = images[pager],
                    modifier = Modifier.fillMaxSize(),
                    error = painterResource(R.drawable.demo_1),
                    contentDescription = "null",
                    contentScale = ContentScale.Crop,
                    onLoading = {
                        isLoading = true
                        isError = false
                    },
                    onSuccess = {
                        isLoading = false
                        isError = false
                    },
                    onError = {
                        isError = true
                        isLoading = false
                    }
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .size(350.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .shimmer(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Image,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }

                if (isError) {
                    Box(
                        modifier = Modifier
                            .size(350.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.errorContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Warning,
                                contentDescription = "network_error",
                                tint = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Failed connection!",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }
        }
    }
}
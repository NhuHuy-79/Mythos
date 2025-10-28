package com.nhuhuy.mythos.creatures.presentation.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.nhuhuy.mythos.R
import com.nhuhuy.mythos.core.utils.capitalizeName
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.valentinilk.shimmer.shimmer

@Composable
fun CreatureItem(
    creature: Creature,
    modifier: Modifier = Modifier,
) {
    var imageLoading by remember { mutableStateOf(true) }
    Row(
        modifier = modifier
            .padding(16.dp)
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(creature.img.firstOrNull())
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .build(),
                onLoading = {
                    imageLoading = true
                },
                onSuccess = {
                    imageLoading = false
                },
                error = painterResource(R.drawable.img_placholder),
                contentDescription = "creature image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (imageLoading){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.onSecondaryContainer)
                        .shimmer()
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = creature.name.capitalizeName(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = creature.category.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.6f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = creature.overview,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
        }
    }
}

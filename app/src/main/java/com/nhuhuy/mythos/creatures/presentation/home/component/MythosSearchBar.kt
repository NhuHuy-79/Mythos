package com.nhuhuy.mythos.creatures.presentation.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun MythosSearchBar(
    onSearchCancel: () -> Unit,
    visible: Boolean,
    focusRequester: FocusRequester,
    modifier: Modifier,
    query: String,
    onSearch: (String) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 200,
                easing = FastOutLinearInEasing
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = 180,
                easing = LinearOutSlowInEasing
            )
        )
    ) {

        LaunchedEffect(visible) {
            if (visible) {
                focusRequester.requestFocus()
            }
        }

        BasicTextField(
            value = query,
            onValueChange = { onSearch(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .focusRequester(focusRequester),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onSearchCancel
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "out of search"
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        innerTextField()
                        if (query.isEmpty()) {
                            Text(
                                text = "",
                                style = MaterialTheme.typography.headlineSmall,
                            )
                        }
                    }
                }
            },
        )
    }
}



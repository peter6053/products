package com.peter.pezesha.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pezesha.R


@Composable
fun Error(
    throwable: Throwable,
    shouldShowRetry: Boolean,
    retryCallback: () -> Unit = {},
) {
    Error(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        throwable = throwable,
        shouldShowRetry = shouldShowRetry,
        retryCallback = retryCallback,
    )
}

@Composable
fun Error(
    modifier: Modifier,
    throwable: Throwable,
    shouldShowRetry: Boolean,
    retryCallback: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = throwable.message ?: stringResource(id = R.string.unknown_error)
        )
        if (shouldShowRetry) {
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = { retryCallback() }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 2.dp),
                    text = stringResource(id = R.string.retry)
                )
            }
        }
    }
}

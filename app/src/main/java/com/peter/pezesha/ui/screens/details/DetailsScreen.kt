package com.peter.pezesha.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.ui.components.CardColumn
import com.peter.pezesha.ui.components.Error
import com.peter.pezesha.ui.components.Image
import com.peter.pezesha.ui.components.LargeText
import com.peter.pezesha.ui.components.Loader
import com.peter.pezesha.ui.components.MediumText
import com.peter.pezesha.ui.components.ParagraphText
import com.peter.pezesha.ui.components.Rating
import com.pezesha.R

const val DEFAULT_PRODUCT_ID = 1
private const val PRODUCT_QTY = 200


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val product by viewModel.product.observeAsState()
    val exception by viewModel.exception.observeAsState()

    Scaffold(
        modifier = Modifier
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            product?.let {
                LargeTopAppBar(
                    title = { Text(text = it.title) },
                    scrollBehavior = scrollBehavior,
                )
            }
        },
    ) { paddingValues ->
        Loader(
            modifier = Modifier
                .padding(paddingValues = paddingValues),
            viewModel = viewModel,
        )

        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues),
        ) {
            product?.let {
                DetailsLayout(product = it)

            } ?: exception?.let {
                Error(
                    throwable = it,
                    shouldShowRetry = true,
                    retryCallback = { viewModel.getProduct() },
                )
            }
        }
    }
}

@Composable
private fun DetailsLayout(product: Product) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .padding(all = 8.dp),
    ) {
        ProductImages(product = product)

        ProductPrice(product = product)

        ProductRating(product = product)

        ProductStock(product = product)

        ProductDetails(product = product)
        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Buy Now")
        }
    }
}

@Composable
private fun ProductImages(product: Product) {
    LazyRow {
        items(items = product.imageUrls) {
            Image(
                modifier = Modifier
                    .padding(all = 8.dp),
                width = 300.dp,
                height = 300.dp,
                url = it,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProductPrice(product: Product) {
    FlowRow(
        modifier = Modifier
            .padding(
                top = 24.dp,
                bottom = 12.dp,
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 12.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        LargeText(text = AnnotatedString(text = stringResource(id = R.string.currency) + product.price))

        Spacer(modifier = Modifier.width(width = 4.dp))

        MediumText(
            text = AnnotatedString(
                text = stringResource(id = R.string.currency) + product.priceWithoutDiscount,
                spanStyle = SpanStyle(textDecoration = TextDecoration.LineThrough),
            ),
            color = MaterialTheme.colorScheme.outline,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
private fun ProductRating(product: Product) {
    CardColumn(
        modifier = Modifier
            .padding(
                top = 12.dp,
                bottom = 12.dp,
            )
    ) {
        Rating(rating = product.rating)
    }
}

@Composable
private fun ProductStock(product: Product) {
    CardColumn(
        modifier = Modifier
            .padding(
                top = 12.dp,
                bottom = 12.dp,
            )
    ) {
        ParagraphText(text = AnnotatedString(text = "${stringResource(id = R.string.in_stock)} ${product.stock}"))

        Spacer(modifier = Modifier.height(height = 8.dp))

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(),
            progress = product.stock.toFloat() / PRODUCT_QTY,
        )
    }
}

@Composable
private fun ProductDetails(product: Product) {
    CardColumn(
        modifier = Modifier
            .padding(
                top = 12.dp,
                bottom = 24.dp,
            )
    ) {
        ParagraphText(text = AnnotatedString(text = product.description))

        ParagraphText(
            text = AnnotatedString(text = "${stringResource(id = R.string.brand)}}"),
            fontWeight = FontWeight.Bold,
        )
    }
}

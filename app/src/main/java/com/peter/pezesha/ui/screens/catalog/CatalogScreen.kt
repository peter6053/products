package com.peter.pezesha.ui.screens.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.ui.components.Error
import com.peter.pezesha.ui.components.ExtraSmallText
import com.peter.pezesha.ui.components.Image
import com.peter.pezesha.ui.components.Loader
import com.peter.pezesha.ui.components.MediumText
import com.peter.pezesha.ui.components.SearchField
import com.peter.pezesha.ui.components.SmallText
import com.peter.pezesha.ui.navigation.LocalNavController
import com.peter.pezesha.ui.navigation.NavigationScreen
import com.pezesha.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
) {
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state = rememberTopAppBarState())
    val staggeredGridState = rememberLazyStaggeredGridState()

    var isSearchVisible by rememberSaveable { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }

    var isCategorySheetVisible by rememberSaveable { mutableStateOf(false) }

    val products by viewModel.productFlow.observeAsState()
    val categories by viewModel.categories.observeAsState()
    val categoriesException by viewModel.exception.observeAsState()

    Scaffold(
        modifier = Modifier
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.products)) },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(
                        onClick = { isSearchVisible = !isSearchVisible }
                    ) { Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(id = R.string.search)) }

//                    IconButton(
//                        onClick = { isCategorySheetVisible = !isCategorySheetVisible }
//                    ) { Icon(imageVector = AdditionalIcons.FilterList, contentDescription = stringResource(id = R.string.categories)) }
                }
            )
        },
    ) { paddingValues ->
        Loader(
            modifier = Modifier
                .padding(paddingValues = paddingValues),
            viewModel = viewModel,
        )

        Column(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                    end = paddingValues.calculateEndPadding(layoutDirection = LocalLayoutDirection.current),
                    top = paddingValues.calculateTopPadding(),
                )
        ) {
            products?.let { productFlow ->
                SearchField(
                    isVisible = isSearchVisible,
                    text = searchText,
                    onTextChanged = { searchText = it },
                    onSearchButtonClicked = {
                        viewModel.setSearchStatement(searchStatement = searchText)
                        scope.launch { staggeredGridState.scrollToItem(index = 0) }
                    },
                )

                ProductList(
                    productFlow = productFlow,
                    state = staggeredGridState,
                    retryCallback = { viewModel.getProductFlow() },
                )
            }

            CategorySheet(
                isVisible = isCategorySheetVisible,
                onDismissRequest = { isCategorySheetVisible = !isCategorySheetVisible },
                categories = categories,
                categoriesError = categoriesException,
                onSelectCategory = { category ->
                    viewModel.setCategory(category = category)
                    scope.launch { staggeredGridState.scrollToItem(index = 0) }
                },
                retryCallback = { viewModel.getCategories() },
            )
        }
    }
}

@Composable
private fun ProductList(
    productFlow: Flow<PagingData<Product>>,
    state: LazyStaggeredGridState,
    retryCallback: () -> Unit,
) {
    val productPagingItems = productFlow.collectAsLazyPagingItems()

    HandlePagingItems(
        productPagingItems = productPagingItems,
        retryCallback = retryCallback,
    )

    LazyVerticalStaggeredGrid(
       // columns = StaggeredGridCells.Adaptive(200.dp),
        columns = StaggeredGridCells.Fixed(2),
        state = state,
        contentPadding = PaddingValues(
            bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding(),
        ),
    ) {
        items(count = productPagingItems.itemCount) { index ->
            productPagingItems[index]?.let { product: Product ->
                ProductListItem(product = product)
            } ?: ProductListItemPlaceholder()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ProductListItem(product: Product) {
    val navController = LocalNavController.current

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        onClick = {
            navController.navigate(NavigationScreen.Details.route!! + "/${product.id}")
        },
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(url = product.thumbnailUrl)

            FlowRow(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                MediumText(
                    color = Color(red = 248, green = 17, blue = 85),
                    text = AnnotatedString(text = stringResource(id = R.string.currency) + product.price)
                )

                Spacer(modifier = Modifier.width(width = 4.dp))

                ExtraSmallText(
                    text = AnnotatedString(
                        text = stringResource(id = R.string.currency) + product.priceWithoutDiscount,
                        spanStyle = SpanStyle(textDecoration = TextDecoration.LineThrough),
                    ),
                )

                Spacer(modifier = Modifier.width(width = 4.dp))

                ExtraSmallText(
                    text = AnnotatedString(text = "-${product.discountPercentage}%"),
                    color = Color(red = 248, green = 17, blue = 85),
                )
            }

            SmallText(text = AnnotatedString(text = product.title))
        }
    }
}

@Composable
private fun ProductListItemPlaceholder() {
    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 300.dp),
        contentAlignment = Alignment.Center
    ) {
        Loader()
    }
}

@Composable
private fun HandlePagingItems(
    productPagingItems: LazyPagingItems<Product>,
    retryCallback: () -> Unit,
) {
    when (val refreshState = productPagingItems.loadState.refresh) {
        is LoadState.Error -> HandlePagerError(
            loadState = refreshState,
            retryCallback = retryCallback,
        )

        is LoadState.Loading -> Loader()
        else -> {}
    }

    HandlePagerError(
        loadState = productPagingItems.loadState.append,
        retryCallback = retryCallback,
    )

    HandlePagerError(
        loadState = productPagingItems.loadState.prepend,
        retryCallback = retryCallback,
    )
}

@Composable
private fun HandlePagerError(
    loadState: LoadState,
    retryCallback: () -> Unit,
) {
    if (loadState is LoadState.Error) {
        Error(
            throwable = loadState.error,
            shouldShowRetry = true,
            retryCallback = retryCallback,
        )
    }
}

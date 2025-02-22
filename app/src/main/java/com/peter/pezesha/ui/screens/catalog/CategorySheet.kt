package com.peter.pezesha.ui.screens.catalog

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.peter.pezesha.domain.model.response.CategoryList
import com.peter.pezesha.ui.components.Error
import com.peter.pezesha.ui.components.LargeText
import com.peter.pezesha.ui.components.MediumText
import com.peter.pezesha.ui.components.ParagraphText
import com.pezesha.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    categories: CategoryList?,
    categoriesError: Throwable?,
    onSelectCategory: (String?) -> Unit,
    retryCallback: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }

    if (isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            windowInsets = WindowInsets(bottom = 0.dp),
        ) {
            categories?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = WindowInsets.navigationBars
                                .asPaddingValues()
                                .calculateBottomPadding()
                        )
                ) {
                    LargeText(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = AnnotatedString(text = stringResource(id = R.string.categories))
                    )

                    Spacer(modifier = Modifier.size(size = 16.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        items(count = categories.categories.size) { index ->
                            FilterChip(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp),
                                selected = categories.categories[index] == selectedCategory,
                                onClick = {
                                    selectedCategory = categories.categories[index]
                                    onSelectCategory(categories.categories[index])
                                },
                                label = { ParagraphText(text = AnnotatedString(text = categories.formattedCategories[index])) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(size = 8.dp))

                    Button(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = {
                            selectedCategory = null
                            onSelectCategory(null)
                        }
                    ) { MediumText(text = AnnotatedString(text = stringResource(id = R.string.reset))) }

                    Spacer(modifier = Modifier.size(size = 16.dp))
                }
            } ?: categoriesError?.let {
                Log.d("throw error", "CategorySheet:${it.message} ")
                Log.d("throw error", "CategorySheet:${it.localizedMessage} ")
                Log.d("throw error", "CategorySheet:${it.cause} ")
                Error(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 300.dp),

                    throwable = it,
                    shouldShowRetry = true,
                    retryCallback = retryCallback,
                )
            }
        }
    }
}

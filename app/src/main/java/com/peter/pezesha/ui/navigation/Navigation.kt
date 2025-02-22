package com.peter.pezesha.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.peter.pezesha.ui.screens.catalog.CatalogScreen
import com.peter.pezesha.ui.screens.details.DEFAULT_PRODUCT_ID
import com.peter.pezesha.ui.screens.details.DetailsScreen
import com.peter.pezesha.ui.screens.details.DetailsViewModel


@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: NavigationScreen = NavigationScreen.Catalog,
) {
    CompositionLocalProvider(LocalNavController provides navHostController) {
        NavHost(
            navController = navHostController,
            startDestination = startDestination.routeWithArguments,
        ) {
            composable(
                route = NavigationScreen.Catalog.routeWithArguments,
            ) {
                CatalogScreen(
                    viewModel = hiltViewModel(),
                )
            }

            composable(
                route = NavigationScreen.Details.routeWithArguments,
                arguments = listOf(navArgument(name = "productId") { type = NavType.IntType })
            ) {
                DetailsScreen(
                    viewModel = hiltViewModel<DetailsViewModel, DetailsViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(productId = it.arguments?.getInt("productId") ?: DEFAULT_PRODUCT_ID)
                        },
                    ),
                )
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!") }

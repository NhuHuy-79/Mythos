package com.nhuhuy.mythos.app.navigation


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nhuhuy.mythos.core.utils.capitalizeName
import com.nhuhuy.mythos.creatures.presentation.detail.DetailScreen
import com.nhuhuy.mythos.creatures.presentation.detail.DetailViewModel
import com.nhuhuy.mythos.creatures.presentation.home.HomeScreen
import com.nhuhuy.mythos.creatures.presentation.home.HomeViewModel
import com.nhuhuy.mythos.creatures.presentation.web.WebScreen

const val ANIMATION_DURATION = 350

@Composable
fun MythosNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.List,
        enterTransition = { fadeIn(tween(ANIMATION_DURATION, easing = FastOutSlowInEasing)) },
        exitTransition = { fadeOut(tween(ANIMATION_DURATION, easing = LinearEasing)) },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(ANIMATION_DURATION, easing = LinearEasing)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(ANIMATION_DURATION, easing = LinearEasing)
            )
        },
        ) {
        composable<Route.List>{
            val listVM: HomeViewModel = hiltViewModel()
            HomeScreen(
                modifier = Modifier,
                onDetail = { id ->
                    navHostController.navigate(Route.Detail(id))
                },
                viewModel = listVM,
                onWiki = {
                    navHostController.navigate(
                        Route.Wiki(
                            url = "https://lovecraft.fandom.com/wiki/Main_Page",
                            name = "THE H.P.LOVECRAFT WIKI"
                        )
                    )
                }
            )
        }

        composable<Route.Detail> { entry ->
            val detailVM: DetailViewModel = hiltViewModel()
            DetailScreen(
                id = entry.arguments?.getInt("id") ?: 0,
                modifier = Modifier,
                viewModel = detailVM,
                onMoreClick = { url, name ->
                    navHostController.navigate(
                        Route.Wiki(
                            url = url,
                            name = name
                        )
                    )
                },
                onCategorySearch = {},
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Route.Wiki>{ entry ->
            WebScreen(
                url = entry.arguments?.getString("url") ?: "",
                name = entry.arguments?.getString("name")?.capitalizeName() ?: "",
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}
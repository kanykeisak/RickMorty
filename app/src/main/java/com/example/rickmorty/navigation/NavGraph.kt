package com.example.rickmorty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickmorty.ui.screens.character.CharacterDetailScreen
import com.example.rickmorty.ui.screens.character.CharactersScreen
import com.example.rickmorty.ui.screens.episode.EpisodeDetailScreen
import com.example.rickmorty.ui.screens.episode.EpisodesScreen
import com.example.rickmorty.ui.screens.favorites.FavoritesScreen
import com.example.rickmorty.ui.screens.location.LocationDetailScreen
import com.example.rickmorty.ui.screens.location.LocationsScreen

sealed class Screen(val route: String) {
    object Characters : Screen("characters")
    object CharacterDetail : Screen("character/{id}") {
        fun createRoute(id: Int) = "character/$id"
    }
    object Episodes : Screen("episodes")
    object EpisodeDetail : Screen("episode/{id}") {
        fun createRoute(id: Int) = "episode/$id"
    }
    object Locations : Screen("locations")
    object LocationDetail : Screen("location/{id}") {
        fun createRoute(id: Int) = "location/$id"
    }
    object Favorites : Screen("favorites")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Characters.route
    ) {
        composable(Screen.Characters.route) {
            CharactersScreen(
                onCharacterClick = { id ->
                    navController.navigate(Screen.CharacterDetail.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.CharacterDetail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            CharacterDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Episodes.route) {
            EpisodesScreen(
                onEpisodeClick = { id ->
                    navController.navigate(Screen.EpisodeDetail.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.EpisodeDetail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            EpisodeDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Locations.route) {
            LocationsScreen(
                onLocationClick = { id ->
                    navController.navigate(Screen.LocationDetail.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.LocationDetail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            LocationDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onCharacterClick = { id ->
                    navController.navigate(Screen.CharacterDetail.createRoute(id))
                }
            )
        }
    }
} 
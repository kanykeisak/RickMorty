package com.example.rickmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickmorty.ui.screens.characters.CharacterDetailScreen
import com.example.rickmorty.ui.screens.characters.CharactersScreen
import com.example.rickmorty.ui.screens.episodes.EpisodesScreen
import com.example.rickmorty.ui.screens.locations.LocationsScreen

sealed class Screen(val route: String) {
    object Characters : Screen("characters")
    object CharacterDetail : Screen("character/{characterId}") {
        fun createRoute(characterId: Int) = "character/$characterId"
    }
    object Locations : Screen("locations")
    object Episodes : Screen("episodes")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Characters.route,
        modifier = modifier
    ) {
        composable(Screen.Characters.route) {
            CharactersScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                }
            )
        }

        composable(
            route = Screen.CharacterDetail.route,
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.IntType
                }
            )
        ) {
            CharacterDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Locations.route) {
            LocationsScreen()
        }

        composable(Screen.Episodes.route) {
            EpisodesScreen()
        }
    }
} 
package com.example.rickmorty.di

import com.example.rickmorty.data.api.RickAndMortyApi
import com.example.rickmorty.data.local.FavoriteCharacterDao
import com.example.rickmorty.data.repository.CharacterRepository
import com.example.rickmorty.data.repository.EpisodeRepository
import com.example.rickmorty.data.repository.LocationRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CharacterRepository(get(), get()) }
    single { EpisodeRepository(get()) }
    single { LocationRepository(get()) }
} 
package com.example.rickmorty.di

import android.content.Context
import com.example.rickmorty.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getDatabase(androidContext()) }
    single { get<AppDatabase>().favoriteCharacterDao() }
} 
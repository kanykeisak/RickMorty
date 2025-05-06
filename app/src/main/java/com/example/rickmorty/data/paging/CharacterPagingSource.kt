package com.example.rickmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorty.data.api.RickAndMortyApi
import com.example.rickmorty.data.model.Character

class CharacterPagingSource(
    private val api: RickAndMortyApi,
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1
            val response = when {
                name != null -> api.searchCharacters(name, page)
                else -> api.getCharacters(page)
            }

            val filteredCharacters = response.results.filter { character ->
                (status == null || character.status.equals(status, ignoreCase = true)) &&
                (species == null || character.species.equals(species, ignoreCase = true))
            }

            LoadResult.Page(
                data = filteredCharacters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
} 
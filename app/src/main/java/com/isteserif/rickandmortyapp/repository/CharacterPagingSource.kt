package com.isteserif.rickandmortyapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.isteserif.rickandmortyapp.api.ApiService
import com.isteserif.rickandmortyapp.model.RickMortyCharacter

// Hangi sayfada olduğumuzu 'Int' (sayfa numarası) ile takip edeceğiz
class CharacterPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, RickMortyCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickMortyCharacter> {

        // 1. Yüklenecek sayfa numarası.
        // Eğer 'key' null ise (yani ilk defa yükleniyorsa), 1. sayfadan başla.
        val currentPage = params.key ?: 1

        return try {
            // 2. API'ye isteği at
            val response = apiService.getCharacters(page = currentPage)
            val characters = response.body()?.results ?: emptyList()

            // 3. API isteği başarılıysa:
            LoadResult.Page(
                data = characters, // Çektiğimiz karakter listesi
                prevKey = if (currentPage == 1) null else currentPage - 1, // Önceki sayfa
                nextKey = if (characters.isEmpty()) null else currentPage + 1 // Sonraki sayfa
            )

        } catch (e: Exception) {
            // 4. API isteği başarısız olursa (örn: internet yok):
            LoadResult.Error(e)
        }
    }

    // Bu, ekran yenilendiğinde hangi sayfadan devam edeceğini bulur.
    override fun getRefreshKey(state: PagingState<Int, RickMortyCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
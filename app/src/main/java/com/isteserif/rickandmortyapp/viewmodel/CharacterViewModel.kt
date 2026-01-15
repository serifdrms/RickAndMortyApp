package com.isteserif.rickandmortyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.isteserif.rickandmortyapp.model.RickMortyCharacter
import com.isteserif.rickandmortyapp.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class CharacterViewModel : ViewModel() {

    // 1. Depo Müdürümüzü (Repository) oluşturuyoruz.
    private val repository = CharacterRepository()

    /**
     * 2. Karakter listesini çeken 'Flow' (Veri Akışı).
     * Bu, Arayüz (Fragment) tarafından 'dinlenecek' olan şeydir.
     */
    val characters: Flow<PagingData<RickMortyCharacter>> = repository.getCharacters()
        // 3. .cachedIn(viewModelScope)
        // Bu o 'sihirli' kısımdır.
        // "Bu veriyi, ViewModel (Genel Müdür) hayatta olduğu sürece
        // hafızada tut (cache)" demektir.
        // Ekran dönse bile veri gitmez.
        .cachedIn(viewModelScope)
}
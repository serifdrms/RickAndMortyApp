package com.isteserif.rickandmortyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.isteserif.rickandmortyapp.model.RickMortyCharacter
import com.isteserif.rickandmortyapp.repository.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModel : ViewModel() {

    private val repository = CharacterRepository()
    
    private val currentQuery = MutableStateFlow("")

    val characters: Flow<PagingData<RickMortyCharacter>> = currentQuery.flatMapLatest { query ->
        repository.getCharacters(if (query.isEmpty()) null else query)
    }.cachedIn(viewModelScope)

    fun searchCharacters(query: String) {
        currentQuery.value = query
    }
}
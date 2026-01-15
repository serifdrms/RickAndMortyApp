package com.isteserif.rickandmortyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isteserif.rickandmortyapp.model.RickMortyCharacter
import com.isteserif.rickandmortyapp.repository.CharacterRepository
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val repository = CharacterRepository()

    // _character: Bu, "Müdür"ün sakladığı özel veridir (değiştirilebilir).
    private val _character = MutableLiveData<RickMortyCharacter>()
    // character: Bu, "Garson"un (Fragment) dinlediği veridir (değiştirilemez).
    val character: LiveData<RickMortyCharacter> = _character

    // Hata durumu için
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Bu fonksiyon, Fragment tarafından çağrılacak.
     * Gelen ID ile API'den veriyi çeker.
     */
    fun getCharacter(id: Int) {
        // 'viewModelScope.launch' -> "Asenkron bir iş başlat"
        viewModelScope.launch {
            try {
                val response = repository.getCharacter(id)
                if (response.isSuccessful) {
                    // Cevap başarılıysa, _character'ın "değerini" güncelle.
                    // "Garson" (Fragment) bunu otomatik olarak fark edecek.
                    _character.postValue(response.body())
                } else {
                    _error.postValue("Hata: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Hata: ${e.message}")
            }
        }
    }
}
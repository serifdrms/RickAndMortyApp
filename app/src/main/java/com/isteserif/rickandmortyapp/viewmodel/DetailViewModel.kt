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

    // _character: sadece bu sınıfın içindeki veri değiştirilebilir (backing property)
    private val _character = MutableLiveData<RickMortyCharacter>()
    // character: değiştirilemez yani "encapsulation" metodu (derste öğrenmiştik).
    val character: LiveData<RickMortyCharacter> = _character

    // Hata durumu için
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Bu fonksiyon, Fragment tarafından çağrılacak.
     * Gelen ID ile API'den veriyi çeker.
     */
    fun getCharacter(id: Int) {
        // 'viewModelScope.launch' veri çekme işlemi uzun sürerse
        // arka planda asenkron çalışmasını sağlar yoksa uygulama donar
        // ayrıca uygulamadan çıkılırsa istek iptal edilir, memory leak önlenir.
        viewModelScope.launch {
            try {
                val response = repository.getCharacter(id)
                if (response.isSuccessful) {
                    // Cevap başarılıysa, postvalue ile LiveData'yı günceller.
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
package kz.kolesateam.confapp.favorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val favoritesLiveData: MutableLiveData<List<EventData>> = MutableLiveData()

    fun getFavoritesLiveData(): LiveData<List<EventData>> = favoritesLiveData

    fun onStart() {
        retrieveFavoriteEvents()
    }

    fun onFavoriteRemove(event: EventData) {
        favoritesRepository.removeFavorite(event.id)
        retrieveFavoriteEvents()
    }

    private fun retrieveFavoriteEvents() {
        favoritesLiveData.value = favoritesRepository.getFavorites()
    }
}
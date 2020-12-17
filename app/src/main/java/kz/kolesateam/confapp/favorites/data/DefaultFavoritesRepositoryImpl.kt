package kz.kolesateam.confapp.favorites.data

import kz.kolesateam.confapp.events.domain.models.EventData
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository

class DefaultFavoritesRepositoryImpl : FavoritesRepository {

    private val favorites: MutableList<EventData> = mutableListOf()

    override fun getFavorites(): List<EventData> = favorites

    override fun addFavorite(event: EventData) {
        favorites.add(event)
    }
}
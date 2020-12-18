package kz.kolesateam.confapp.favorites.data

import kz.kolesateam.confapp.events.domain.models.EventData
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository

class DefaultFavoritesRepositoryImpl : FavoritesRepository {

    private val favorites: MutableMap<Int, EventData> = mutableMapOf()

    override fun getFavorites(): List<EventData> {
        return favorites.values.toList()
    }

    override fun addFavorite(event: EventData) {
        favorites[event.id] = event
    }

    override fun removeFavorite(eventId: Int) {
        favorites.remove(eventId)
    }

    override fun isFavorite(eventId: Int): Boolean {
        return favorites.containsKey(eventId)
    }
}
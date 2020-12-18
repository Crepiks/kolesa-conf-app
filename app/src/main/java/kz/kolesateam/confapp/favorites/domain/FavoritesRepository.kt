package kz.kolesateam.confapp.favorites.domain

import kz.kolesateam.confapp.events.domain.models.EventData

interface FavoritesRepository {

    fun getFavorites(): List<EventData>

    fun addFavorite(event: EventData)

    fun removeFavorite(eventId: Int)
}
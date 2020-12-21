package kz.kolesateam.confapp.favorites.domain

import kz.kolesateam.confapp.common.models.EventData

interface FavoritesRepository {

    fun getFavorites(): List<EventData>

    fun addFavorite(event: EventData)

    fun removeFavorite(eventId: Int)

    fun isFavorite(eventId: Int): Boolean
}
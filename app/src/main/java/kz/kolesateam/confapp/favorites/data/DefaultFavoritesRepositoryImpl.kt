package kz.kolesateam.confapp.favorites.data

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.MapType
import kz.kolesateam.confapp.events.domain.models.EventData
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository
import java.io.FileInputStream
import java.io.FileOutputStream

private const val FAVORITE_EVENTS_FILE_NAME = "favorite_events.json"
private const val TAG = "FavoritesRepository"

class DefaultFavoritesRepositoryImpl(
    private val context: Context,
    private val objectMapper: ObjectMapper
) : FavoritesRepository {

    private val favorites: MutableMap<Int, EventData> = mutableMapOf()

    init {
        favorites.putAll(getFavoritesFromFile())
    }

    override fun getFavorites(): List<EventData> {
        return favorites.values.toList()
    }

    override fun addFavorite(event: EventData) {
        favorites[event.id] = event
        saveFavoritesToFile()
    }

    override fun removeFavorite(eventId: Int) {
        favorites.remove(eventId)
        saveFavoritesToFile()
    }

    override fun isFavorite(eventId: Int): Boolean {
        return favorites.containsKey(eventId)
    }

    private fun saveFavoritesToFile() {
        val favoritesJsonString: String = objectMapper.writeValueAsString(favorites)
        val fileOutputStream: FileOutputStream = context.openFileOutput(
            FAVORITE_EVENTS_FILE_NAME,
            Context.MODE_PRIVATE
        )
        fileOutputStream.write(favoritesJsonString.toByteArray())
        fileOutputStream.close()
    }

    private fun getFavoritesFromFile(): Map<Int, EventData> = try {
        val fileInputStream: FileInputStream = context.openFileInput(FAVORITE_EVENTS_FILE_NAME)
        val favoritesJsonString: String =
            fileInputStream.bufferedReader().readLines().joinToString()
        Log.d(TAG, favoritesJsonString)
        val mapType: MapType = objectMapper.typeFactory.constructMapType(
            Map::class.java,
            Int::class.java,
            EventData::class.java
        )

        objectMapper.readValue(favoritesJsonString, mapType)
    } catch (e: Exception) {
        Log.e(TAG, e.localizedMessage)
        mapOf()
    }
}
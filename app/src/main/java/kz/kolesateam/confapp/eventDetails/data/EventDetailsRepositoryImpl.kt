package kz.kolesateam.confapp.eventDetails.data

import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.eventDetails.data.mappers.EventApiDataMapper
import kz.kolesateam.confapp.eventDetails.data.models.EventApiData
import kz.kolesateam.confapp.eventDetails.domain.EventDetailsRepository
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository

class EventDetailsRepositoryImpl(
    private val eventDetailsDataSource: EventDetailsDataSource,
    private val favoritesRepository: FavoritesRepository
) : EventDetailsRepository {

    private lateinit var eventApiData: EventApiData

    override fun loadEvent(eventId: Int): ResponseData<EventData, String> = try {
        val response = eventDetailsDataSource.getEvent(eventId).execute()
        if (response.isSuccessful) {
            val eventApiData: EventApiData = response.body()!!
            this.eventApiData = eventApiData
            ResponseData.Success(
                EventApiDataMapper(
                    favoritesRepository
                ).map(eventApiData)
            )
        } else {
            ResponseData.Error(response.errorBody().toString())
        }
    } catch (e: Exception) {
        ResponseData.Error(e.localizedMessage)
    }

    override fun getEvent(): EventData {
        return EventApiDataMapper(favoritesRepository).map(eventApiData)
    }
}
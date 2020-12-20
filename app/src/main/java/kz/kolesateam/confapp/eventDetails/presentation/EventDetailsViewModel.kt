package kz.kolesateam.confapp.eventDetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.common.models.ProgressStatus
import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.eventDetails.domain.EventDetailsRepository
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository
import kz.kolesateam.confapp.notifications.EventsNotificationAlarm
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

private const val DEFAULT_EVENT_ID = 0

class EventDetailsViewModel(
    private val eventDetailsRepository: EventDetailsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val eventsNotificationAlarm: EventsNotificationAlarm
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressStatus> = MutableLiveData()
    private val eventLiveData: MutableLiveData<EventData> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    private var eventId: Int = DEFAULT_EVENT_ID

    fun getProgressLiveData(): LiveData<ProgressStatus> = progressLiveData
    fun getEventLiveData(): LiveData<EventData> = eventLiveData
    fun getErrorLiveData(): LiveData<String> = errorLiveData


    fun onStart(eventId: Int) {
        this.eventId = eventId
        fetchEvent(eventId)
    }

    fun onFavoriteAdd(event: EventData) {
        favoritesRepository.addFavorite(event)
        scheduleNotification(event)
        refreshEvent()
    }

    fun onFavoriteRemove(event: EventData) {
        favoritesRepository.removeFavorite(event.id)
        cancelNotification(event)
        refreshEvent()
    }

    private fun scheduleNotification(event: EventData) {
        eventsNotificationAlarm.scheduleNotification(
            event.id,
            event.title,
            event.schedule.startTime
        )
    }

    private fun cancelNotification(event: EventData) {
        eventsNotificationAlarm.cancelNotification(event.id)
    }

    private fun fetchEvent(eventId: Int) {
        progressLiveData.value = ProgressStatus.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val response: ResponseData<EventData, String> = withContext(Dispatchers.IO) {
                eventDetailsRepository.loadEvent(eventId)
            }

            when (response) {
                is ResponseData.Success -> eventLiveData.value = prepareEventData(response.result)
                is ResponseData.Error -> errorLiveData.value = response.error
            }
            progressLiveData.value = ProgressStatus.Finished
        }
    }

    private fun refreshEvent() {
        eventLiveData.value = prepareEventData(eventDetailsRepository.getEvent())
    }

    private fun prepareEventData(event: EventData): EventData {
        val schedule = EventData.Schedule(
            startTime = getHours(event.schedule.startTime),
            endTime = getHours(
                event.schedule.endTime
            )
        )
        return EventData(
            id = event.id,
            schedule = schedule,
            title = event.title,
            description = event.description,
            place = event.place,
            speaker = event.speaker,
            isFavorite = event.isFavorite
        )
    }

    private fun getHours(dateTimeString: String): String {
        val parsedDateTime: ZonedDateTime = ZonedDateTime.parse(dateTimeString)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return parsedDateTime.format(formatter)
    }
}
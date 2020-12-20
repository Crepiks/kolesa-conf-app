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
import kz.kolesateam.confapp.eventDetails.data.models.EventApiData
import kz.kolesateam.confapp.eventDetails.domain.EventDetailsRepository
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

private const val DEFAULT_EVENT_ID = 0

class EventDetailsViewModel(
    private val eventDetailsRepository: EventDetailsRepository
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

    private fun fetchEvent(eventId: Int) {
        progressLiveData.value = ProgressStatus.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val response: ResponseData<EventData, String> = withContext(Dispatchers.IO) {
                eventDetailsRepository.loadEvent(eventId)
            }

            when (response) {
                is ResponseData.Success -> eventLiveData.value = response.result
                is ResponseData.Error -> errorLiveData.value = response.error
            }
            progressLiveData.value = ProgressStatus.Finished
        }
    }

    private fun refreshEvent(): EventData {
        return eventDetailsRepository.getEvent()
    }
}
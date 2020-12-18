package kz.kolesateam.confapp.branchEvents.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.branchEvents.domain.BranchEventsRepository
import kz.kolesateam.confapp.branchEvents.presentation.models.BranchEventListItem
import kz.kolesateam.confapp.branchEvents.presentation.models.EventItem
import kz.kolesateam.confapp.branchEvents.presentation.models.HeaderItem
import kz.kolesateam.confapp.common.models.ProgressStatus
import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.events.domain.models.EventData
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository

private const val DEFAULT_BRANCH_ID = 0
private const val DEFAULT_BRANCH_TITLE = ""
private const val TAG = "BranchEventsViewModel"

class BranchEventsViewModel(
    private val branchEventsRepository: BranchEventsRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressStatus> = MutableLiveData()
    private val branchEventsListLiveData: MutableLiveData<List<BranchEventListItem>> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressStatus> = progressLiveData
    fun getBranchEventListLiveData(): LiveData<List<BranchEventListItem>> = branchEventsListLiveData
    fun getErrorLiveData(): LiveData<String> = errorLiveData

    private var branchId = DEFAULT_BRANCH_ID
    private var branchTitle = DEFAULT_BRANCH_TITLE

    fun onStart(
        branchId: Int,
        branchTitle: String
    ) {
        this.branchId = branchId
        this.branchTitle = branchTitle
        fetchData()
    }

    fun onFavoriteAdd(event: EventData) {
        favoritesRepository.addFavorite(event)
        refreshBranchEventList()
    }

    fun onFavoriteRemove(event: EventData) {
        favoritesRepository.removeFavorite(event.id)
        refreshBranchEventList()
    }

    private fun fetchData() {
        progressLiveData.value = ProgressStatus.Loading
        viewModelScope.launch(Dispatchers.Main) {
            var response: ResponseData<List<EventData>, String> =
                withContext(Dispatchers.IO) {
                    branchEventsRepository.loadBranchEvents(branchId = branchId)
                }
            when (response) {
                is ResponseData.Success -> {
                    val eventList = response.result
                    branchEventsListLiveData.value = getBranchEventList(eventList)
                }
                is ResponseData.Error -> errorLiveData.value = response.error
            }
            progressLiveData.value = ProgressStatus.Finished
        }
    }

    private fun getBranchEventList(eventList: List<EventData>): List<BranchEventListItem> {
        val headerItem = HeaderItem(title = branchTitle)
        val eventItemList = eventList.map { event ->
            EventItem(data = event)
        }
        return listOf(headerItem) + eventItemList
    }

    private fun refreshBranchEventList() {
        val eventList = branchEventsRepository.getBranchEvents()
        branchEventsListLiveData.value = getBranchEventList(eventList)
    }
}
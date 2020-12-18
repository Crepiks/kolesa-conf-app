package kz.kolesateam.confapp.upcomingEvents.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.common.models.ProgressStatus
import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.events.domain.models.BranchData
import kz.kolesateam.confapp.upcomingEvents.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.upcomingEvents.presentation.models.BranchItem
import kz.kolesateam.confapp.upcomingEvents.presentation.models.HeaderItem
import kz.kolesateam.confapp.upcomingEvents.presentation.models.UpcomingEventListItem

private const val DEFAULT_USER_NAME = ""
private const val TAG = "UpcomingEventsViewModel"

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressStatus> = MutableLiveData()
    private val upcomingEventsLiveData: MutableLiveData<List<UpcomingEventListItem>> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    private var userName: String = DEFAULT_USER_NAME

    fun onStart(userName: String) {
        this.userName = userName
        fetchData()
    }

    fun getProgressLiveData(): LiveData<ProgressStatus> = progressLiveData
    fun getUpcomingEventsLiveData(): LiveData<List<UpcomingEventListItem>> = upcomingEventsLiveData
    fun getErrorLiveData(): LiveData<String> = errorLiveData

    private fun fetchData() {
        Log.d(TAG, "Started fetching")
        progressLiveData.value = ProgressStatus.Loading
        viewModelScope.launch {
            val response: ResponseData<List<BranchData>, String> = withContext(Dispatchers.IO) {
                upcomingEventsRepository.getUpcomingEvents()
            }
            Log.d(TAG, response.toString())
            when (response) {
                is ResponseData.Success -> {
                    val branchList = response.result
                    upcomingEventsLiveData.value = getUpcomingEventList(branchList)
                }
                is ResponseData.Error -> {
                    errorLiveData.value = response.error
                }
            }
            progressLiveData.value = ProgressStatus.Finished
        }
    }

    private fun getUpcomingEventList(branchList: List<BranchData>): List<UpcomingEventListItem> {
        val headerListItem = HeaderItem(
            userName = userName
        )
        val branchListItems = branchList.map { branchListItem ->
            BranchItem(
                data = branchListItem
            )
        }
        return listOf(headerListItem) + branchListItems
    }

}
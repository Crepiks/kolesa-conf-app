package kz.kolesateam.confapp.upcomingEvents.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.common.models.ProgressStatus
import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.upcomingEvents.data.models.BranchApiData
import kz.kolesateam.confapp.upcomingEvents.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.upcomingEvents.presentation.models.BranchItem
import kz.kolesateam.confapp.upcomingEvents.presentation.models.HeaderItem
import kz.kolesateam.confapp.upcomingEvents.presentation.models.UpcomingEventListItem

private const val DEFAULT_USER_NAME = ""

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
        progressLiveData.value = ProgressStatus.Loading
        viewModelScope.launch {
            val response: ResponseData<List<BranchApiData>, String> = withContext(Dispatchers.IO) {
                upcomingEventsRepository.getUpcomingEvents()
            }
            when (response) {
                is ResponseData.Success -> {
                    val branchList = response.result
                    upcomingEventsLiveData.value = getUpcomingEventList(branchList)
                }
                is ResponseData.Error -> {
                    errorLiveData.value = response.error
                }
            }
        }
        progressLiveData.value = ProgressStatus.Finished
    }

    private fun getUpcomingEventList(branchList: List<BranchApiData>): List<UpcomingEventListItem> {
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
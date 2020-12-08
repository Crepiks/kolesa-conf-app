package kz.kolesateam.confapp.branchEvents.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import kz.kolesateam.confapp.branchEvents.domain.BranchEventsRepository
import kz.kolesateam.confapp.branchEvents.presentation.models.BranchEventListItem
import kz.kolesateam.confapp.branchEvents.presentation.models.EventItem
import kz.kolesateam.confapp.branchEvents.presentation.models.HeaderItem
import kz.kolesateam.confapp.common.models.ProgressStatus
import kz.kolesateam.confapp.common.models.ResponseData

private const val DEFAULT_BRANCH_ID = 0
private const val DEFAULT_BRANCH_TITLE = ""

class BranchEventsViewModel(
    private val branchEventsRepository: BranchEventsRepository
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressStatus> = MutableLiveData()
    private val branchEventsListLiveData: MutableLiveData<List<BranchEventListItem>> = MutableLiveData()
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

    private fun fetchData() {
        progressLiveData.value = ProgressStatus.Loading
        viewModelScope.launch(Dispatchers.Main) {
            var response: ResponseData<List<EventApiData>, String> =
                withContext(Dispatchers.IO) {
                    branchEventsRepository.getBranchEvents(branchId = branchId)
                }
            when (response) {
                is ResponseData.Success -> {
                    val branchList = response.result
                    branchEventsListLiveData.value = getBranchEventList(branchList)
                }
                is ResponseData.Error -> errorLiveData.value = response.error
            }
            progressLiveData.value = ProgressStatus.Finished
        }
    }

    private fun getBranchEventList(eventList: List<EventApiData>): List<BranchEventListItem> {
        val headerItem = HeaderItem(title = branchTitle)
        val eventItemList = eventList.map { event ->
            EventItem(data = event)
        }
        return listOf(headerItem) + eventItemList
    }
}
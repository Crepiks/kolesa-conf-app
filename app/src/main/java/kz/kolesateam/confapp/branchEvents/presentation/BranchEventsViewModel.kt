package kz.kolesateam.confapp.branchEvents.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.branchEvents.data.BranchEventsDataSource
import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import kz.kolesateam.confapp.branchEvents.domain.BranchEventsRepository
import kz.kolesateam.confapp.common.models.ProgressStatus
import kz.kolesateam.confapp.common.models.ResponseData
import retrofit2.Response

private const val DEFAULT_BRANCH_ID = 0

class BranchEventsViewModel(
    private val branchEventsRepository: BranchEventsRepository
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressStatus> = MutableLiveData()
    private val branchListLiveData: MutableLiveData<List<EventApiData>> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressStatus> = progressLiveData
    fun getBranchListLiveData(): LiveData<List<EventApiData>> = branchListLiveData
    fun getErrorLiveData(): LiveData<String> = errorLiveData

    private var branchId = DEFAULT_BRANCH_ID

    fun onStart(branchId: Int) {
        this.branchId = branchId
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
                    branchListLiveData.value = branchList
                }
                is ResponseData.Error -> errorLiveData.value = response.error
            }
            progressLiveData.value = ProgressStatus.Finished
        }
    }
}
package kz.kolesateam.confapp.branchEvents.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.branchEvents.data.BranchEventsApiClient
import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import kz.kolesateam.confapp.common.models.ProgressStatus
import retrofit2.Response

private const val DEFAULT_BRANCH_ID = 0

class BranchEventsViewModel(
    private val branchEventsApiClient: BranchEventsApiClient
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressStatus> = MutableLiveData()
    private val branchListLiveData: MutableLiveData<List<EventApiData>> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressStatus> = progressLiveData
    fun getBranchListLiveData(): LiveData<List<EventApiData>> = branchListLiveData
    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    private var branchId = DEFAULT_BRANCH_ID

    fun onStart(branchId: Int) {
        this.branchId = branchId
        fetchData()
    }

    private fun fetchData() {
        progressLiveData.value = ProgressStatus.Loading
        viewModelScope.launch(Dispatchers.Main) {
            var response: Response<List<EventApiData>>
            withContext(Dispatchers.IO) {
                response = branchEventsApiClient.getBranchEvents(branchId = branchId).execute()
            }
            if (response.isSuccessful) {
                val branchList = response.body()!!
                branchListLiveData.value = branchList
            }
            progressLiveData.value = ProgressStatus.Finished
        }
    }
}
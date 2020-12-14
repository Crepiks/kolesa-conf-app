package kz.kolesateam.confapp.upcomingEvents.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.extension.gone
import kz.kolesateam.confapp.extension.show
import kz.kolesateam.confapp.upcomingEvents.data.models.BranchApiData
import kz.kolesateam.confapp.upcomingEvents.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.upcomingEvents.presentation.models.BranchItem
import kz.kolesateam.confapp.upcomingEvents.presentation.models.HeaderItem
import kz.kolesateam.confapp.upcomingEvents.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.upcomingEvents.presentation.view.UpcomingEventListAdapter
import org.koin.android.ext.android.inject
import java.lang.ref.WeakReference

private const val PREFERENCE_NAME = "user_name"
private const val USERNAME_DEFAULT_VALUE = ""

class UpcomingEventsActivity : AppCompatActivity() {

    private val upcomingEventsRepository: UpcomingEventsRepository by inject()

    private val branchListAdapter = UpcomingEventListAdapter(
        onBranchClick = ::handleBranchClick,
        onEventClick = ::handleEventClick
    )

    private lateinit var progressBar: ProgressBar
    private lateinit var branchList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
        fetchData()
    }

    private fun handleBranchClick(branchId: Int, branchTitle: String) {
        val router = UpcomingEventsRouter(WeakReference(this))
        router.navigateToBranchEventsScreen(
            branchId = branchId,
            branchTitle = branchTitle
        )
    }

    private fun handleEventClick(eventTitle: String) {
        Toast.makeText(this, eventTitle, Toast.LENGTH_SHORT).show()
    }

    private fun bindViews() {
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
        branchList = findViewById(R.id.activity_upcoming_events_events_list)
        branchList.layoutManager = LinearLayoutManager(this)
        branchList.adapter = branchListAdapter
    }

    private fun fetchData() {
        startLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val response: ResponseData<List<BranchApiData>, String> = withContext(Dispatchers.IO) {
                upcomingEventsRepository.getUpcomingEvents()
            }
            if (response is ResponseData.Success) {
                val branchList = response.result
                showResult(branchList)
            }
            finishLoading()
        }
    }

    private fun startLoading() {
        progressBar.show()
    }

    private fun finishLoading() {
        progressBar.gone()
    }

    private fun showResult(branchList: List<BranchApiData>) {
        val upcomingEventItemList = getUpcomingEventList(branchList)
        branchListAdapter.setList(upcomingEventItemList)
    }

    private fun getUpcomingEventList(branchList: List<BranchApiData>): List<UpcomingEventListItem> {
        val userName: String = getUserName()
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

    private fun getUserName(): String {
        val sharedPref: SharedPreferences =
            getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(PREFERENCE_NAME, USERNAME_DEFAULT_VALUE)
            ?: USERNAME_DEFAULT_VALUE
    }
}
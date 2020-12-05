package kz.kolesateam.confapp.branchEvents.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import kz.kolesateam.confapp.branchEvents.presentation.models.BranchEventListItem
import kz.kolesateam.confapp.branchEvents.presentation.models.EventItem
import kz.kolesateam.confapp.branchEvents.presentation.models.HeaderItem
import kz.kolesateam.confapp.branchEvents.presentation.view.EventListAdapter
import kz.kolesateam.confapp.common.models.ProgressStatus
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.extension.gone
import kz.kolesateam.confapp.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "BranchEventsActivity"
private const val DEFAULT_BRANCH_ID = 0
private const val DEFAULT_BRANCH_TITLE = ""

class BranchEventsActivity : AppCompatActivity() {

    private val branchEventsViewModel: BranchEventsViewModel by viewModel()
    private val eventListAdapter = EventListAdapter()

    private var branchId: Int = DEFAULT_BRANCH_ID
    private lateinit var branchTitle: String
    private lateinit var backArrow: View
    private lateinit var eventListRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_events)

        branchId = getBranchId()
        branchTitle = getBranchTitle()

        bindViews()

        branchEventsViewModel.onStart(branchId = branchId)
        observerBranchEventsViewModel()
    }

    private fun getBranchId(): Int {
        return intent.getIntExtra("BRANCH_ID", DEFAULT_BRANCH_ID)
    }

    private fun getBranchTitle(): String {
        val branchTitle = intent.getStringExtra("BRANCH_TITLE");
        return branchTitle ?: DEFAULT_BRANCH_TITLE
    }

    private fun observerBranchEventsViewModel() {
        branchEventsViewModel.getProgressLiveData().observe(this, ::handleLoadingStatusChange)
        branchEventsViewModel.getBranchListLiveData().observe(this, ::showEventList)
        branchEventsViewModel.getErrorLiveData().observe(this, ::showErrorMessage)
    }

    private fun bindViews() {
        backArrow = findViewById(R.id.activity_branch_events_back_arrow)
        backArrow.setOnClickListener {
            handleBackArrowClick()
        }
        progressBar = findViewById(R.id.activity_branch_events_progress_bar)
        eventListRecyclerView = findViewById(R.id.activity_branch_events_event_list)
        eventListRecyclerView.adapter = eventListAdapter
        eventListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun handleBackArrowClick() {
        val intent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(intent)
    }

    private fun handleLoadingStatusChange(status: ProgressStatus) {
        when (status) {
            ProgressStatus.Loading -> progressBar.show()
            else -> progressBar.gone()
        }
    }

    private fun showEventList(eventList: List<EventApiData>) {
        val branchEventList = getBranchEventList(eventList)
        eventListAdapter.setList(branchEventList)
    }

    private fun showErrorMessage(error: Exception) {
        val errorMessage = error.localizedMessage
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun getBranchEventList(eventList: List<EventApiData>): List<BranchEventListItem> {
        val headerItem = HeaderItem(title = branchTitle)
        val eventItemList = eventList.map { event ->
            EventItem(data = event)
        }
        return listOf(headerItem) + eventItemList
    }
}
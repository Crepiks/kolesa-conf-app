package kz.kolesateam.confapp.branchEvents.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branchEvents.presentation.models.BranchEventListItem
import kz.kolesateam.confapp.branchEvents.presentation.view.EventListAdapter
import kz.kolesateam.confapp.common.models.ProgressStatus
import kz.kolesateam.confapp.eventDetails.EventDetailsActivity
import kz.kolesateam.confapp.events.domain.models.EventData
import kz.kolesateam.confapp.extension.gone
import kz.kolesateam.confapp.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DEFAULT_BRANCH_ID = 0
private const val DEFAULT_BRANCH_TITLE = ""
private const val BRANCH_ID_KEY = "BRANCH_ID"
private const val BRANCH_TITLE_KEY = "BRANCH_TITLE"
private const val TAG = "BranchEventsActivity";

class BranchEventsActivity : AppCompatActivity() {

    private val branchEventsViewModel: BranchEventsViewModel by viewModel()
    private val eventListAdapter = EventListAdapter(onFavoriteClick = ::handleFavoriteClick)

    private lateinit var backArrow: View
    private lateinit var eventListRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var favoritesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_events)

        bindViews()

        observerBranchEventsLiveData()
        branchEventsViewModel.onStart(
            branchId = getBranchId(),
            branchTitle = getBranchTitle()
        )
    }

    private fun handleFavoriteClick(event: EventData, isFavorite: Boolean) {
        when (isFavorite) {
            true -> branchEventsViewModel.onFavoriteAdd(event)
            false -> branchEventsViewModel.onFavoriteRemove(event)
        }
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

        favoritesButton = findViewById(R.id.activity_branch_events_favorites_button)
        favoritesButton.setOnClickListener {
            val intent = Intent(this, EventDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observerBranchEventsLiveData() {
        branchEventsViewModel.getProgressLiveData().observe(this, ::handleLoadingStatusChange)
        branchEventsViewModel.getBranchEventListLiveData().observe(this, ::showBranchEventList)
        branchEventsViewModel.getErrorLiveData().observe(this, ::showErrorMessage)
    }

    private fun getBranchId(): Int {
        return intent.getIntExtra(BRANCH_ID_KEY, DEFAULT_BRANCH_ID)
    }

    private fun getBranchTitle(): String {
        val branchTitle = intent.getStringExtra(BRANCH_TITLE_KEY);
        return branchTitle ?: DEFAULT_BRANCH_TITLE
    }

    private fun handleBackArrowClick() {
        finish()
    }

    private fun handleLoadingStatusChange(status: ProgressStatus) {
        when (status) {
            ProgressStatus.Loading -> progressBar.show()
            ProgressStatus.Finished -> progressBar.gone()
        }
    }

    private fun showBranchEventList(branchEventList: List<BranchEventListItem>) {
        eventListAdapter.setList(branchEventList)
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
package kz.kolesateam.confapp.upcomingEvents.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branchEvents.presentation.BranchEventsRouter
import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.common.models.ProgressStatus
import kz.kolesateam.confapp.extension.gone
import kz.kolesateam.confapp.extension.show
import kz.kolesateam.confapp.upcomingEvents.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.upcomingEvents.presentation.view.UpcomingEventListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference

private const val PREFERENCE_NAME = "user_name"
private const val USERNAME_DEFAULT_VALUE = ""

class UpcomingEventsActivity : AppCompatActivity() {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()

    private val branchListAdapter = UpcomingEventListAdapter(
        onBranchClick = ::handleBranchClick,
        onEventClick = ::handleEventClick,
        onFavoriteClick = ::handleFavoriteClick
    )

    private lateinit var progressBar: ProgressBar
    private lateinit var branchList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()

        observerUpcomingEventsLiveData()
        upcomingEventsViewModel.onStart(userName = getUserName())
    }

    private fun observerUpcomingEventsLiveData() {
        upcomingEventsViewModel.getProgressLiveData().observe(this, ::handleProgressStatusChange)
        upcomingEventsViewModel.getUpcomingEventsLiveData()
            .observe(this, ::showUpcomingEventsChange)
        upcomingEventsViewModel.getErrorLiveData().observe(this, ::showErrorMessage)
    }

    private fun handleProgressStatusChange(progressStatus: ProgressStatus) {
        when (progressStatus) {
            ProgressStatus.Loading -> startLoading()
            ProgressStatus.Finished -> finishLoading()
        }
    }

    private fun handleFavoriteClick(event: EventData, isFavorite: Boolean) {
        when (isFavorite) {
            true -> upcomingEventsViewModel.onFavoriteAdd(event)
            false -> upcomingEventsViewModel.onFavoriteRemove(event)
        }
    }

    private fun handleBranchClick(branchId: Int, branchTitle: String) {
        val branchEventsIntent = BranchEventsRouter().createIntent(
            context = this,
            branchId = branchId,
            branchTitle = branchTitle
        )
        startActivity(branchEventsIntent)
    }

    private fun showUpcomingEventsChange(upcomingEventList: List<UpcomingEventListItem>) {
        branchListAdapter.setList(upcomingEventList)
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
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

    private fun startLoading() {
        progressBar.show()
    }

    private fun finishLoading() {
        progressBar.gone()
    }

    private fun getUserName(): String {
        val sharedPref: SharedPreferences =
            getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(PREFERENCE_NAME, USERNAME_DEFAULT_VALUE)
            ?: USERNAME_DEFAULT_VALUE
    }
}
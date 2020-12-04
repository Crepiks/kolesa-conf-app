package kz.kolesateam.confapp.branchEvents.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branchEvents.data.BranchEventsApiClient
import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import kz.kolesateam.confapp.branchEvents.presentation.models.BranchEventListItem
import kz.kolesateam.confapp.branchEvents.presentation.models.EventItem
import kz.kolesateam.confapp.branchEvents.presentation.models.HeaderItem
import kz.kolesateam.confapp.branchEvents.presentation.view.EventListAdapter
import kz.kolesateam.confapp.common.API_BASE_URL
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.extension.gone
import kz.kolesateam.confapp.extension.show
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl(API_BASE_URL)
    .addConverterFactory(JacksonConverterFactory.create()).build();
val apiClient: BranchEventsApiClient = apiRetrofit.create(BranchEventsApiClient::class.java)

class BranchEventsActivity : AppCompatActivity() {

    private val eventListAdapter = EventListAdapter()

    private var branchId: Int = 0
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
    }

    private fun getBranchId(): Int {
        return intent.getIntExtra("BRANCH_ID", 0)
    }

    private fun getBranchTitle(): String {
        val branchTitle = intent.getStringExtra("BRANCH_TITLE");
        return branchTitle ?: ""
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
        fetchData()
    }

    private fun handleBackArrowClick() {
        val intent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(intent)
    }

    private fun fetchData() {
        startLoading()
        apiClient.getBranchEvents(branchId = branchId).enqueue(object : Callback<List<EventApiData>> {
            override fun onResponse(
                call: Call<List<EventApiData>>,
                response: Response<List<EventApiData>>
            ) {
                finishLoading()
                if (response.isSuccessful) {
                    val branchList = response.body()!!
                    showResult(branchList)
                }
            }

            override fun onFailure(call: Call<List<EventApiData>>, t: Throwable) {
                finishLoading()
            }
        })
    }

    private fun startLoading() {
        progressBar.show()
    }

    private fun finishLoading() {
        progressBar.gone()
    }

    private fun showResult(eventList: List<EventApiData>) {
        val branchEventList = getBranchEventList(eventList)
        eventListAdapter.setList(branchEventList)
    }

    private fun getBranchEventList(eventList: List<EventApiData>): List<BranchEventListItem> {
        val headerItem = HeaderItem(title = branchTitle)
        val eventItemList = eventList.map { event ->
            EventItem(data = event)
        }
        return listOf(headerItem) + eventItemList
    }
}
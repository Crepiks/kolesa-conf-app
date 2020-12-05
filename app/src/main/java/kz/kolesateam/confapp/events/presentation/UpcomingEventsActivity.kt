package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.branchEvents.presentation.BranchEventsActivity
import kz.kolesateam.confapp.events.data.UpcomingEventsApiClient
import kz.kolesateam.confapp.events.presentation.models.BranchItem
import kz.kolesateam.confapp.events.presentation.models.HeaderItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventListAdapter
import kz.kolesateam.confapp.extension.gone
import kz.kolesateam.confapp.extension.show
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

const val API_BASE_URL = "http://37.143.8.68:2020/"

val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl(API_BASE_URL)
    .addConverterFactory(JacksonConverterFactory.create()).build();
val apiClient: UpcomingEventsApiClient = apiRetrofit.create(UpcomingEventsApiClient::class.java)

private const val PREFERENCE_NAME = "user_name"
private const val USERNAME_DEFAULT_VALUE = ""

class UpcomingEventsActivity : AppCompatActivity() {

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
        val branchEventsIntent = Intent(this, BranchEventsActivity::class.java)
        branchEventsIntent.putExtra("BRANCH_ID", branchId)
        branchEventsIntent.putExtra("BRANCH_TITLE", branchTitle)
        startActivity(branchEventsIntent)
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
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(
                call: Call<List<BranchApiData>>,
                response: Response<List<BranchApiData>>
            ) {
                finishLoading()
                if (response.isSuccessful) {
                    val branchList = response.body()!!
                    showResult(branchList)
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
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
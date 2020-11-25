package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.UpcomingEventsApiClient
import kz.kolesateam.confapp.extension.gone
import kz.kolesateam.confapp.extension.show
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

val apiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://37.143.8.68:2020/")
        .addConverterFactory(JacksonConverterFactory.create()).build();
val apiClient: UpcomingEventsApiClient = apiRetrofit.create(UpcomingEventsApiClient::class.java)

private const val PREFERENCE_NAME = "user_name"

class UpcomingEventsActivity : AppCompatActivity() {

    private val branchListAdapter = BranchListAdapter(
            onBranchClick = ::handleBranchClick,
            onEventClick = ::handleEventClick
    )

    private lateinit var progressBar: ProgressBar
    private lateinit var greeting: TextView
    private lateinit var branchList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
        fetchData()
    }

    private fun handleBranchClick(branchTitle: String) {
        Toast.makeText(this, branchTitle, Toast.LENGTH_SHORT).show()
    }

    private fun handleEventClick(eventTitle: String) {
        Toast.makeText(this, eventTitle, Toast.LENGTH_SHORT).show()
    }

    private fun bindViews() {
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
        greeting = findViewById(R.id.activity_upcoming_events_greeting)
        val userName: String = getUserName()
        greeting.text = resources.getString(R.string.activity_upcoming_events_greeting_fmt, userName)
        branchList = findViewById(R.id.activity_upcoming_events_events_list)
        branchList.layoutManager = LinearLayoutManager(this)
        branchList.adapter = branchListAdapter
    }

    private fun getUserName(): String {
        val sharedPref: SharedPreferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(PREFERENCE_NAME, "Unknown").toString()
    }

    private fun fetchData() {
        startLoading()
        apiClient.getUpcomingEvents().enqueue(object: Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
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
        branchListAdapter.setList(branchList)
    }
}
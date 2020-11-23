package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
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

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
    }

    private fun fetchData() {
        startLoading()
        apiClient.getUpcomingEvents().enqueue(object: Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
                finishLoading()
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
}
package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

val apiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://37.143.8.68:2020/")
        .addConverterFactory(JacksonConverterFactory.create()).build();
val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

fun View.show() : View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide() : View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var loadDataResultTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var loadSyncButton: Button
    private lateinit var loadAsyncButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        loadDataResultTextView = findViewById(R.id.activity_upcoming_events_request_result)
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)

        loadSyncButton = findViewById(R.id.activity_upcoming_events_sync_loading_button)
        loadSyncButton.setOnClickListener {
            loadSyncData()
        }

        loadAsyncButton = findViewById(R.id.activity_upcoming_events_async_loading_button)
        loadAsyncButton.setOnClickListener {
            loadAsyncData()
        }
    }

    private fun loadAsyncData() {
        startLoading()
        apiClient.getUpcomingEvents().enqueue(object: Callback<JsonNode> {
            override fun onResponse(call: Call<JsonNode>, response: Response<JsonNode>) {
                finishLoading()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    changeText(body.toString(), R.color.activity_upcoming_events_async_text_color)
                }
            }

            override fun onFailure(call: Call<JsonNode>, t: Throwable) {
                finishLoading()
                val errorMessage = t.localizedMessage;
                changeText(errorMessage, R.color.activity_upcoming_events_error_text_color)
            }
        })
    }

    private fun loadSyncData() {
        Thread {
            try {
                runOnUiThread {
                    startLoading()
                }
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    runOnUiThread {
                        finishLoading()
                        changeText(body.toString(), R.color.activity_upcoming_events_sync_text_color)
                    }
                }
            } catch(e: Exception) {
                runOnUiThread {
                    finishLoading()
                    val errorMessage = e.localizedMessage
                    changeText(errorMessage, R.color.activity_upcoming_events_error_text_color)
                }
            }
        }.start()
    }

    private fun startLoading() {
        loadSyncButton.hide()
        loadAsyncButton.hide()
        loadDataResultTextView.hide()
        progressBar.show()
    }

    private fun finishLoading() {
        loadSyncButton.show()
        loadAsyncButton.show()
        loadDataResultTextView.show()
        progressBar.hide()
    }

    private fun changeText(text: String, color: Int) {
        loadDataResultTextView.text = text
        loadDataResultTextView.setTextColor( ContextCompat.getColor(this, color))
    }
}
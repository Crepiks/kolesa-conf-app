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
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import org.json.JSONArray
import org.json.JSONObject
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
        apiClient.getParsedUpcomingEvents().enqueue(object: Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
                finishLoading()
                if (response.isSuccessful) {
                    val branchesList: List<BranchApiData> = response.body()!!
                    changeText(branchesList.toString(), R.color.activity_upcoming_events_async_text_color)
                } else {
                    val errorMessage = response.errorBody().toString()
                    changeText(errorMessage, R.color.activity_upcoming_events_error_text_color)
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                finishLoading()
                val errorMessage = t.localizedMessage;
                changeText(errorMessage, R.color.activity_upcoming_events_error_text_color)
            }
        })
    }

    private fun loadSyncData() {
        startLoading()
        Thread {
            try {
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    val responseJsonString = body.toString()
                    val responseJsonArray = JSONArray(responseJsonString)
                    val branchesList = parseBranchesJsonArray(responseJsonArray)
                    runOnUiThread {
                        changeText(branchesList.toString(), R.color.activity_upcoming_events_sync_text_color)
                    }
                } else {
                    val errorMessage = response.errorBody().toString()
                    runOnUiThread {
                        changeText(errorMessage, R.color.activity_upcoming_events_error_text_color)
                    }
                }
            } catch(e: Exception) {
                val errorMessage = e.localizedMessage
                runOnUiThread {
                    changeText(errorMessage, R.color.activity_upcoming_events_error_text_color)
                }
            }
            runOnUiThread {
                finishLoading()
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

    private fun parseBranchesJsonArray(branchesJsonArray: JSONArray): List<BranchApiData> {
        val branchList = mutableListOf<BranchApiData>()
        for (index in 0 until branchesJsonArray.length()) {
            val branchJsonObject: JSONObject = branchesJsonArray[index] as? JSONObject ?: continue
            val branch = parseBranchJsonObject(branchJsonObject)
            branchList.add(branch)
        }
        return branchList
    }

    private fun parseBranchJsonObject(branchJsonObject: JSONObject): BranchApiData {
        val id = branchJsonObject.getInt("id")
        val title = branchJsonObject.getString("title")
        val eventsJsonArray = branchJsonObject.getJSONArray("events")

        val eventsList = mutableListOf<EventApiData>()
        for (index in 0 until eventsJsonArray.length()) {
            val eventJsonObject = (eventsJsonArray[index] as? JSONObject) ?: continue
            val event = parseEventJsonObject(eventJsonObject)
            eventsList.add(event)
        }
        return BranchApiData(
                id = id,
                title = title,
                events = eventsList
        )
    }

    private fun parseEventJsonObject(eventJsonObject: JSONObject): EventApiData {
        val id = eventJsonObject.getInt("id")
        val startTime = eventJsonObject.getString("startTime")
        val endTime = eventJsonObject.getString("endTime")
        val title = eventJsonObject.getString("title")
        val description = eventJsonObject.getString("description")
        val place = eventJsonObject.getString("place")
        val speakerJsonObject: JSONObject? = eventJsonObject.get("speaker") as? JSONObject
        var speaker: SpeakerApiData? = null

        speakerJsonObject?.let {
            speaker = parseSpeakerJsonObject(speakerJsonObject)
        }

        return EventApiData(
                id = id,
                startTime = startTime,
                endTime = endTime,
                title = title,
                description = description,
                place = place,
                speaker = speaker
        )
    }

    private fun parseSpeakerJsonObject(speakerJsonObject: JSONObject): SpeakerApiData {
        val id = speakerJsonObject.getInt("id")
        val fullName = speakerJsonObject.getString("fullName")
        val job = speakerJsonObject.getString("job")
        val photoUrl = speakerJsonObject.getString("photoUrl")

        return SpeakerApiData(
                id = id,
                fullName = fullName,
                job = job,
                photoUrl = photoUrl
        )
    }
}
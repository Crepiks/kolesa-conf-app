package kz.kolesateam.confapp.eventDetails.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.eventDetails.domain.EventDetailsRepository
import org.koin.android.ext.android.inject

private const val DEFAULT_EVENT_ID = 0
private const val TAG = "EventDetailsActivity"

class EventDetailsActivity : AppCompatActivity() {

    private val eventDetailsRepository: EventDetailsRepository by inject()

    private lateinit var backButton: View
    private lateinit var favoriteButton: View
    private lateinit var speakerImage: ImageView
    private lateinit var speakerFullName: TextView
    private lateinit var speakerJob: TextView
    private lateinit var timeAndPlace: TextView
    private lateinit var title: TextView
    private lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        val eventId: Int = getEventId()
        Log.d(TAG, eventId.toString())
        CoroutineScope(Dispatchers.IO).launch {
            val response = eventDetailsRepository.loadEvent(eventId)
            Log.d(TAG, response.toString())
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun getEventId(): Int {
        return intent.getIntExtra(EVENT_ID_EXTRA_KEY, DEFAULT_EVENT_ID)
    }
}
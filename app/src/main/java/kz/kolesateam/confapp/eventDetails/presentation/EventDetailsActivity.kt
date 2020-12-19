package kz.kolesateam.confapp.eventDetails.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R

private const val TAG = "EventDetailsActivity"
private const val DEFAULT_EVENT_ID = 0

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        initViews()
        setEventId()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun initViews() {
        textView = findViewById(R.id.activity_event_detail_text)
    }

    private fun setEventId() {
        val eventId: Int = intent.getIntExtra(EVENT_ID_EXTRA_KEY, DEFAULT_EVENT_ID)
        textView.text = eventId.toString()
    }
}
package kz.kolesateam.confapp.eventDetails.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.common.models.ProgressStatus
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

private const val DEFAULT_EVENT_ID = 0
private const val TAG = "EventDetailsActivity"
const val TIME_AND_PLACE_FORMAT = "%s - %s • %s"

class EventDetailsActivity : AppCompatActivity() {

    private val eventDetailsViewModel: EventDetailsViewModel by viewModel()

    private lateinit var backButton: View
    private lateinit var favoriteButton: ImageView
    private lateinit var speakerImage: ImageView
    private lateinit var speakerFullName: TextView
    private lateinit var speakerJob: TextView
    private lateinit var timeAndPlace: TextView
    private lateinit var title: TextView
    private lateinit var description: TextView

    private lateinit var event: EventData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        initViews()

        val eventId: Int = getEventId()
        observerEventDetailsLiveData()
        eventDetailsViewModel.onStart(eventId)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun initViews() {
        backButton = findViewById(R.id.activity_event_details_back_arrow)
        favoriteButton = findViewById(R.id.activity_event_details_favorite_button)
        speakerImage = findViewById(R.id.activity_event_details_speaker_photo)
        speakerFullName = findViewById(R.id.activity_event_details_speaker_full_name)
        speakerJob = findViewById(R.id.activity_event_details_speaker_job)
        timeAndPlace = findViewById(R.id.activity_event_details_time_and_place)
        title = findViewById(R.id.activity_event_details_title)
        description = findViewById(R.id.activity_event_details_description)

        setListeners()
    }

    private fun setListeners() {
        backButton.setOnClickListener {
            handleBackButtonClick()
        }

        favoriteButton.setOnClickListener {
            handleFavoriteButtonClick()
        }
    }

    private fun handleBackButtonClick() {
        finish()
    }

    private fun handleFavoriteButtonClick() {
        if (!::event.isInitialized) return

        when (event.isFavorite) {
            true -> eventDetailsViewModel.onFavoriteRemove(event)
            false -> eventDetailsViewModel.onFavoriteAdd(event)
        }
    }

    private fun observerEventDetailsLiveData() {
        eventDetailsViewModel.getProgressLiveData().observe(this, ::handleLoadingStatusChange)
        eventDetailsViewModel.getEventLiveData().observe(this, ::handleEventDataChange)
        eventDetailsViewModel.getErrorLiveData().observe(this, ::showErrorMessage)
    }

    private fun getEventId(): Int {
        return intent.getIntExtra(EVENT_ID_EXTRA_KEY, DEFAULT_EVENT_ID)
    }

    private fun handleLoadingStatusChange(status: ProgressStatus) {

    }

    private fun handleEventDataChange(event: EventData) {
        this.event = event
        setEventData(event)
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun setEventData(event: EventData) {
        speakerFullName.text = event.speaker.fullName
        speakerJob.text = event.speaker.job
        timeAndPlace.text = TIME_AND_PLACE_FORMAT.format(
            getHours(event.schedule.startTime),
            getHours(event.schedule.endTime),
            event.place
        )
        title.text = event.title
        description.text = event.description

        setFavoriteButtonResource(event.isFavorite)
        setSpeakerImage(event.speaker.photoUrl)
    }

    private fun getHours(dateTimeString: String): String {
        val parsedDateTime: ZonedDateTime = ZonedDateTime.parse(dateTimeString)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return parsedDateTime.format(formatter)
    }

    private fun setFavoriteButtonResource(isFavorite: Boolean) {
        when (isFavorite) {
            true -> favoriteButton.setImageResource(R.drawable.ic_favorite_white_filled)
            false -> favoriteButton.setImageResource(R.drawable.ic_favorite_white_border)
        }
    }

    private fun setSpeakerImage(imageUrl: String) {
        Glide
            .with(speakerImage.context)
            .load(imageUrl)
            .into(speakerImage)
    }
}
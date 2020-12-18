package kz.kolesateam.confapp.eventDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository
import org.koin.android.ext.android.inject

class EventDetailsActivity : AppCompatActivity() {

    private val favoritesRepository: FavoritesRepository by inject()

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        textView = findViewById(R.id.activity_event_detail_text)
        textView.text = favoritesRepository.getFavorites().toString()
    }
}
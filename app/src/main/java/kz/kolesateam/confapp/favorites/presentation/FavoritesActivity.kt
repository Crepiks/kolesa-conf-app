package kz.kolesateam.confapp.favorites.presentation

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.eventDetails.presentation.EventDetailsRouter
import kz.kolesateam.confapp.extension.gone
import kz.kolesateam.confapp.extension.show
import kz.kolesateam.confapp.favorites.presentation.view.EventListAdapter
import kz.kolesateam.confapp.upcomingEvents.presentation.UpcomingEventsRouter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesActivity : AppCompatActivity() {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private val eventListAdapter = EventListAdapter(
        onFavoriteClick = ::handleFavoriteClick,
        onEventClick = ::handleEventClick
    )

    private lateinit var eventListRecyclerView: RecyclerView
    private lateinit var homeButton: Button
    private lateinit var emptyContainer: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        initViews()
        observeFavoritesLiveData()
        favoritesViewModel.onStart()
    }

    override fun onResume() {
        super.onResume()

        favoritesViewModel.onStart()
    }

    private fun initViews() {
        eventListRecyclerView = findViewById(R.id.activity_favorites_event_list)
        eventListRecyclerView.adapter = eventListAdapter
        eventListRecyclerView.layoutManager = LinearLayoutManager(this)

        emptyContainer = findViewById(R.id.activity_favorites_empty_container)

        homeButton = findViewById(R.id.activity_favorites_home_button)
        homeButton.setOnClickListener {
            navigateToHomeScreen()
        }
    }

    private fun observeFavoritesLiveData() {
        favoritesViewModel.getFavoritesLiveData().observe(this, ::handleFavoritesChange)
    }

    private fun handleFavoriteClick(event: EventData) {
        favoritesViewModel.onFavoriteRemove(event)
    }

    private fun handleEventClick(event: EventData) {
        val eventDetailsIntent: Intent = EventDetailsRouter().createIntent(
            context = this,
            eventId = event.id
        )
        startActivity(eventDetailsIntent)
    }

    private fun navigateToHomeScreen() {
        val upcomingEventsIntent: Intent = UpcomingEventsRouter().createIntent(this)
        startActivity(upcomingEventsIntent)
    }

    private fun handleFavoritesChange(eventList: List<EventData>) {
        if (eventList.isEmpty()) {
            emptyContainer.show()
            eventListRecyclerView.gone()
        } else {
            emptyContainer.gone()
            eventListRecyclerView.show()
            setEventList(eventList)
        }
    }

    private fun setEventList(eventList: List<EventData>) {
        eventListAdapter.setList(eventList)
    }
}
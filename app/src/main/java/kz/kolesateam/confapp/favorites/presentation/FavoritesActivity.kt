package kz.kolesateam.confapp.favorites.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.favorites.presentation.view.EventListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "FavoritesActivity"

class FavoritesActivity : AppCompatActivity() {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private val eventListAdapter = EventListAdapter(onFavoriteClick = ::handleFavoriteClick)

    private lateinit var eventListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        initViews()
        observeFavoritesLiveData()
        favoritesViewModel.onStart()
    }

    private fun initViews() {
        eventListRecyclerView = findViewById(R.id.activity_favorites_event_list)
        eventListRecyclerView.adapter = eventListAdapter
        eventListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeFavoritesLiveData() {
        favoritesViewModel.getFavoritesLiveData().observe(this, ::setEventList)
    }

    private fun handleFavoriteClick(event: EventData) {
        favoritesViewModel.onFavoriteRemove(event)
    }

    private fun setEventList(eventList: List<EventData>) {
        Log.d(TAG, eventList.toString())
        eventListAdapter.setList(eventList)
    }
}
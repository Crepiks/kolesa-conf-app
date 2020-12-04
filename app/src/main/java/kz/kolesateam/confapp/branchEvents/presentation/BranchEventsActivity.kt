package kz.kolesateam.confapp.branchEvents.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branchEvents.presentation.models.BranchEventListItem
import kz.kolesateam.confapp.branchEvents.presentation.models.HeaderItem
import kz.kolesateam.confapp.branchEvents.presentation.view.EventListAdapter
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity

class BranchEventsActivity : AppCompatActivity() {

    private val eventListAdapter = EventListAdapter()

    private lateinit var backArrow: View
    private lateinit var eventListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_events)
        bindViews()
    }

    private fun bindViews() {
        backArrow = findViewById(R.id.activity_branch_events_back_arrow)
        backArrow.setOnClickListener {
            handleBackArrowClick()
        }
        eventListRecyclerView = findViewById(R.id.activity_branch_events_event_list)
        eventListRecyclerView.adapter = eventListAdapter
        eventListRecyclerView.layoutManager = LinearLayoutManager(this)
        eventListAdapter.setList(getHeaderListItem("Hello"))
    }

    private fun handleBackArrowClick() {
        val intent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(intent)
    }

    private fun getHeaderListItem(titleText: String): List<BranchEventListItem> {
        val headerItem = HeaderItem(title = titleText)
        return listOf(headerItem)
    }
}
package kz.kolesateam.confapp.branchEvents.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity

class BranchEventsActivity : AppCompatActivity() {

    private lateinit var backArrow: View

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
    }

    private fun handleBackArrowClick() {
        val intent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(intent)
    }
}
package kz.kolesateam.confapp.upcomingEvents.presentation

import android.content.Intent
import kz.kolesateam.confapp.branchEvents.presentation.BranchEventsActivity
import java.lang.ref.WeakReference

private const val BRANCH_ID_KEY = "BRANCH_ID"
private const val BRANCH_TITLE_KEY = "BRANCH_TITLE"

class UpcomingEventsRouter(
    private val activity: WeakReference<UpcomingEventsActivity>
) {

    fun navigateToBranchEventsScreen(branchId: Int, branchTitle: String) {
        val intent = Intent(activity.get(), BranchEventsActivity::class.java)
        intent.apply {
            putExtra(BRANCH_ID_KEY, branchId)
            putExtra(BRANCH_TITLE_KEY, branchTitle)
        }
        activity.get()?.startActivity(intent)
    }
}
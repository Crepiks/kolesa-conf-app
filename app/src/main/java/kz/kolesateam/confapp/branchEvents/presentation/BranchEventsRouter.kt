package kz.kolesateam.confapp.branchEvents.presentation

import android.content.Context
import android.content.Intent

const val BRANCH_ID_EXTRA_KEY = "branch_id"
const val BRANCH_TITLE_EXTRA_KEY = "branch_title"

class BranchEventsRouter {

    fun createIntent(
        context: Context,
        branchId: Int,
        branchTitle: String
    ): Intent {
        val intent = Intent(context, BranchEventsActivity::class.java)
        intent.apply {
            putExtra(BRANCH_ID_EXTRA_KEY, branchId)
            putExtra(BRANCH_TITLE_EXTRA_KEY, branchTitle)
        }
        return intent
    }
}
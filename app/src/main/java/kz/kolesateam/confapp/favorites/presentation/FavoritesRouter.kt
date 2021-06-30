package kz.kolesateam.confapp.favorites.presentation

import android.content.Context
import android.content.Intent

class FavoritesRouter {

    fun createIntent(context: Context): Intent {
        return Intent(context, FavoritesActivity::class.java)
    }
}
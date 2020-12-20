package kz.kolesateam.confapp.favorites.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesActivity : AppCompatActivity() {

    private val favoritesViewModel: FavoritesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
    }
}
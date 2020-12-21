package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.upcomingEvents.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.upcomingEvents.presentation.UpcomingEventsRouter
import java.lang.ref.WeakReference

private const val PREFERENCE_NAME = "user_name"

class HelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val nameEditText = findViewById<EditText>(R.id.activity_hello_name_input)
        val continueButton = findViewById<Button>(R.id.activity_hello_continue_button)

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val isInputEmpty: Boolean = s.toString().isBlank()
                continueButton.isEnabled = !isInputEmpty
            }
        })

        continueButton.setOnClickListener {
            val userName = nameEditText.text.toString()
            storeUserName(userName)
            navigateToUpcomingEventsScreen()
        }
    }

    private fun storeUserName(userName: String) {
        val sharedPref: SharedPreferences =
            getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(PREFERENCE_NAME, userName)
        editor.apply()
    }

    private fun navigateToUpcomingEventsScreen() {
        val upcomingEventsIntent =
            UpcomingEventsRouter().createIntent(context = this)
        startActivity(upcomingEventsIntent)
    }
}
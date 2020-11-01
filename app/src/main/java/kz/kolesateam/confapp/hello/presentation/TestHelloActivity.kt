package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kz.kolesateam.confapp.R

private const val PREFERENCE_NAME = "user-name"

class TestHelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_hello)

        val nameTextView = findViewById<TextView>(R.id.activity_test_hello_greeting_text)
        val userName = retrieveUserName()
        nameTextView.text = resources.getString(R.string.greeting, userName)
    }

    private fun retrieveUserName(): String {
        val sharedPref: SharedPreferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(PREFERENCE_NAME, "").toString()
    }
}
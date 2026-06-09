package com.example.sharedspacexml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        val chatButton = findViewById<Button>(R.id.chatButton)
        val notesButton = findViewById<Button>(R.id.notesButton)
        val albumButton = findViewById<Button>(R.id.albumButton)
        val todoButton = findViewById<Button>(R.id.todoButton)
        val voiceButton = findViewById<Button>(R.id.voiceButton)
        val locationButton = findViewById<Button>(R.id.locationButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        welcomeText.text = "Welcome to SharedSpace! 🐻"

        chatButton.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }

        notesButton.setOnClickListener {
            Toast.makeText(this, "Notes coming soon! 📝", Toast.LENGTH_SHORT).show()
        }

        albumButton.setOnClickListener {
            Toast.makeText(this, "Album coming soon! 📷", Toast.LENGTH_SHORT).show()
        }

        todoButton.setOnClickListener {
            Toast.makeText(this, "To-Do coming soon! ✅", Toast.LENGTH_SHORT).show()
        }

        voiceButton.setOnClickListener {
            Toast.makeText(this, "Voice coming soon! 🎤", Toast.LENGTH_SHORT).show()
        }

        locationButton.setOnClickListener {
            Toast.makeText(this, "Location coming soon! 📍", Toast.LENGTH_SHORT).show()
        }

        logoutButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
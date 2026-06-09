package com.example.sharedspacexml

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class VoiceActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)

        auth = FirebaseAuth.getInstance()
        setupVoice()
    }

    private fun setupVoice() {
        val recordButton = findViewById<MaterialButton>(R.id.recordButton)

        recordButton.setOnClickListener {
            Toast.makeText(this, "Voice recording feature coming soon! 🎤", Toast.LENGTH_SHORT).show()
        }

        val sendButton = findViewById<MaterialButton>(R.id.sendButton)
        sendButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("voice_note", "voice_placeholder")
            startActivity(intent)
        }
    }
}
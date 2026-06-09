package com.example.sharedspacexml

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class LocationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        auth = FirebaseAuth.getInstance()
        setupLocation()
    }

    private fun setupLocation() {
        val shareLocationButton = findViewById<MaterialButton>(R.id.shareLocationButton)

        shareLocationButton.setOnClickListener {
            shareLocation()
        }
    }

    private fun shareLocation() {
        // For now, we'll use a mock location
        val mockLocation = mapOf(
            "latitude" to 37.7749,
            "longitude" to -122.4194,
            "address" to "San Francisco, CA"
        )

        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("share_location", true)
        Toast.makeText(this, "Location ready to share! 📍", Toast.LENGTH_SHORT).show()

        // You can start ChatActivity or just go back
        finish()
    }
}
package com.example.sharedspacexml

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NotesActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setupNotes()
    }

    private fun setupNotes() {
        val titleEditText = findViewById<TextInputEditText>(R.id.titleEditText)
        val contentEditText = findViewById<TextInputEditText>(R.id.contentEditText)
        val saveButton = findViewById<MaterialButton>(R.id.saveButton)

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val content = contentEditText.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please fill both title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveNote(title, content)
        }
    }

    private fun saveNote(title: String, content: String) {
        val currentUser = auth.currentUser
        val noteRef = database.getReference("notes").push()

        val note = mapOf(
            "id" to noteRef.key,
            "title" to title,
            "content" to content,
            "authorId" to currentUser?.uid,
            ("authorName" to currentUser?.displayName ?: "Anonymous") as Pair<String, Any?>,
            "timestamp" to System.currentTimeMillis(),
            "lastEdited" to System.currentTimeMillis()
        )

        noteRef.setValue(note).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Note saved! 📝", Toast.LENGTH_SHORT).show()
                findViewById<TextInputEditText>(R.id.titleEditText).text?.clear()
                findViewById<TextInputEditText>(R.id.contentEditText).text?.clear()
            } else {
                Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
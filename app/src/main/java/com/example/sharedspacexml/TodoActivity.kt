package com.example.sharedspacexml


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class TodoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setupTodo()
    }

    private fun setupTodo() {
        val taskEditText = findViewById<TextInputEditText>(R.id.taskEditText)
        val addButton = findViewById<MaterialButton>(R.id.addButton)

        addButton.setOnClickListener {
            val task = taskEditText.text.toString().trim()

            if (task.isEmpty()) {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            addTask(task)
            taskEditText.text?.clear()
        }
    }

    private fun addTask(task: String) {
        val currentUser = auth.currentUser
        val taskRef = database.getReference("todos").push()

        val todo = mapOf(
            "id" to taskRef.key,
            "task" to task,
            "completed" to false,
            "createdBy" to currentUser?.uid,
            ("createdByName" to currentUser?.displayName ?: "Anonymous") as Pair<String, Any?>,
            "createdAt" to System.currentTimeMillis()
        )

        taskRef.setValue(todo).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Task added! ✅", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
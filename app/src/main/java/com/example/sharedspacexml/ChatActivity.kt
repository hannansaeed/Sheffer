package com.example.sharedspacexml

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        setupChatUI()
        setupRealtimeMessages()
    }

    private fun setupChatUI() {
        adapter = MessageAdapter(messages)
        findViewById<RecyclerView>(R.id.messagesRecyclerView).apply {
            adapter = this@ChatActivity.adapter
            layoutManager = LinearLayoutManager(this@ChatActivity)
        }

        findViewById<MaterialButton>(R.id.sendButton).setOnClickListener {
            val messageText = findViewById<TextInputEditText>(R.id.messageEditText).text.toString()
            if (messageText.isNotEmpty()) {
                sendMessageToFirebase(messageText)
                findViewById<TextInputEditText>(R.id.messageEditText).text?.clear()
            }
        }
    }

    private fun sendMessageToFirebase(text: String) {
        val currentUser = auth.currentUser
        val messageRef = database.getReference("messages").push()

        val message = mapOf(
            "id" to messageRef.key,
            "text" to text,
            "type" to "text",
            "senderId" to currentUser?.uid,
            ("senderName" to currentUser?.email?.split("@")?.first() ?: "User") as Pair<String, Any?>,
            "timestamp" to System.currentTimeMillis()
        )

        messageRef.setValue(message)
    }

    private fun setupRealtimeMessages() {
        val messagesRef = database.getReference("messages")
        messagesRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(ChatMessage::class.java)
                message?.let {
                    messages.add(it)
                    adapter.notifyItemInserted(messages.size - 1)
                    findViewById<RecyclerView>(R.id.messagesRecyclerView).smoothScrollToPosition(messages.size - 1)
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatActivity, "Database error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
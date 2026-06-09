package com.example.sharedspacexml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: ChatMessage) {
            val isCurrentUser = message.senderId == auth.currentUser?.uid

            itemView.findViewById<TextView>(R.id.messageText).text = message.text
            itemView.findViewById<TextView>(R.id.senderName).text = message.senderName
            itemView.findViewById<TextView>(R.id.timestamp).text =
                android.text.format.DateFormat.format("HH:mm", message.timestamp)

            // Simple bubble styling
            if (isCurrentUser) {
                itemView.findViewById<View>(R.id.messageBubble).setBackgroundResource(R.drawable.bubble_outgoing)
                itemView.findViewById<TextView>(R.id.senderName).isVisible = false
            } else {
                itemView.findViewById<View>(R.id.messageBubble).setBackgroundResource(R.drawable.bubble_incoming)
                itemView.findViewById<TextView>(R.id.senderName).isVisible = true
            }
        }
    }
}
package com.example.sharedspacexml

data class ChatMessage(
    val id: String = "",
    val text: String = "",
    val type: String = "text",
    val senderId: String = "",
    val senderName: String = "",
    val timestamp: Long = 0,
    val additionalData: Map<String, Any> = emptyMap()
)
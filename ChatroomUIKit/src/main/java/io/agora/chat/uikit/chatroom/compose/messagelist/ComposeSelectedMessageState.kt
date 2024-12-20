package io.agora.chat.uikit.chatroom.compose.messagelist

import io.agora.chat.uikit.chatroom.service.ChatMessage

/**
 * Represents a state when a message or its reactions were selected.
 *
 * @param message The selected message.
 */
sealed class
ComposeSelectedMessageState(public val message: ChatMessage)

/**
 * Represents a state when a message was selected.
 */
class SelectedMessageOptionsState(message: ChatMessage) :
    ComposeSelectedMessageState(message)


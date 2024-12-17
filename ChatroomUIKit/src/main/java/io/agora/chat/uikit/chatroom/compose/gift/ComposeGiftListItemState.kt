package io.agora.chat.uikit.chatroom.compose.gift

import io.agora.chat.uikit.chatroom.service.GiftEntityProtocol

sealed class ComposeGiftListItemState

data class ComposeGiftItemState(
    val gift: GiftEntityProtocol
): ComposeGiftListItemState()
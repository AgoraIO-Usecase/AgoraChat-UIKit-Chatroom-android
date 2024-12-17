package io.agora.chat.uikit.chatroom.viewmodel.gift

import io.agora.chat.uikit.chatroom.ChatroomUIKitClient
import io.agora.chat.uikit.chatroom.UIChatroomService
import io.agora.chat.uikit.chatroom.compose.gift.ComposeGiftItemState
import io.agora.chat.uikit.chatroom.compose.gift.ComposeGiftListItemState
import io.agora.chat.uikit.chatroom.service.ChatMessage
import io.agora.chat.uikit.chatroom.service.GiftEntityProtocol
import io.agora.chat.uikit.chatroom.service.GiftReceiveListener
import io.agora.chat.uikit.chatroom.viewmodel.ComposeBaseListViewModel

class ComposeGiftListViewModel(
    private val giftItems: List<ComposeGiftItemState> = emptyList(),
    private val service: UIChatroomService,
): ComposeBaseListViewModel<ComposeGiftListItemState>(
    contentList = giftItems
), GiftReceiveListener {

    fun registerGiftListener() {
        service.getGiftService().bindGiftListener(this)
    }

    private fun unregisterGiftListener() {
        service.getGiftService().unbindGiftListener(this)
    }

    override fun onCleared() {
        super.onCleared()
        unregisterGiftListener()
    }

    override fun onGiftReceived(roomId: String, gift: GiftEntityProtocol?, message: ChatMessage) {
        super.onGiftReceived(roomId, gift, message)
        if (!ChatroomUIKitClient.getInstance().getUseGiftsInMsg()){
            gift?.let { giftEntity ->
                ChatroomUIKitClient.getInstance().parseUserInfo(message)?.let {
                    giftEntity.sendUser = it
                }
                addDateToIndex(data = ComposeGiftItemState(giftEntity))
            }
        }
    }

}
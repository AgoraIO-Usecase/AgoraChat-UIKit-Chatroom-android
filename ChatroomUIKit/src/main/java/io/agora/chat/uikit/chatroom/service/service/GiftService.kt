package io.agora.chat.uikit.chatroom.service

import io.agora.chat.uikit.chatroom.model.UserInfoProtocol

interface GiftService : GiftMessageHandleService {

    fun bindGiftListener(listener: GiftReceiveListener)

    fun unbindGiftListener(listener: GiftReceiveListener)

}

data class GiftEntityProtocol(
    val giftId: String,
    val giftName: String,
    val giftPrice: String,
    val giftCount: Int = 1,
    val giftIcon: String,
    val giftEffect: String,
//    val selected: Boolean,
    var sendUser: UserInfoProtocol
)
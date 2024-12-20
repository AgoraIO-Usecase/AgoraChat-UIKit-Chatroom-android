package io.agora.chat.uikit.chatroom.service.serviceImpl

import io.agora.chat.uikit.chatroom.ChatroomResultEvent
import io.agora.chat.uikit.chatroom.ChatroomUIKitClient
import io.agora.chat.uikit.chatroom.model.UIConstant
import io.agora.chat.uikit.chatroom.service.ChatCallback
import io.agora.chat.uikit.chatroom.service.ChatClient
import io.agora.chat.uikit.chatroom.service.ChatCustomMessageBody
import io.agora.chat.uikit.chatroom.service.ChatError
import io.agora.chat.uikit.chatroom.service.ChatMessage
import io.agora.chat.uikit.chatroom.service.ChatMessageType
import io.agora.chat.uikit.chatroom.service.ChatType
import io.agora.chat.uikit.chatroom.service.GiftEntityProtocol
import io.agora.chat.uikit.chatroom.service.GiftReceiveListener
import io.agora.chat.uikit.chatroom.service.GiftService
import io.agora.chat.uikit.chatroom.service.OnError
import io.agora.chat.uikit.chatroom.service.OnValueSuccess
import io.agora.chat.uikit.chatroom.service.transfer
import io.agora.chat.uikit.chatroom.utils.GsonTools
import org.json.JSONObject

class GiftServiceImpl: GiftService {
    private val chatManager by lazy { ChatClient.getInstance().chatManager() }
    private val listeners = mutableListOf<GiftReceiveListener>()
    @Synchronized
    override fun bindGiftListener(listener: GiftReceiveListener) {
        if (!listeners.contains(listener)){
            listeners.add(listener)
            ChatroomUIKitClient.getInstance().updateChatroomGiftListener(listeners)
        }
    }

    @Synchronized
    override fun unbindGiftListener(listener: GiftReceiveListener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener)
            ChatroomUIKitClient.getInstance().updateChatroomGiftListener(listeners)
        }
    }

    override fun sendGift(entity: GiftEntityProtocol, onSuccess: OnValueSuccess<ChatMessage>, onError: OnError) {
        val message = ChatMessage.createSendMessage(ChatMessageType.CUSTOM)
        val customBody = ChatCustomMessageBody(UIConstant.CHATROOM_UIKIT_GIFT)
        val userInfoProtocol = ChatroomUIKitClient.getInstance().getCurrentUser().transfer()
        val user = GsonTools.beanToString(userInfoProtocol)
        val gift = GsonTools.beanToString(entity)
        val infoMap = mutableMapOf(UIConstant.CHATROOM_UIKIT_GIFT_INFO to gift)
        customBody.params = infoMap
        message.setAttribute(UIConstant.CHATROOM_UIKIT_USER_INFO, user?.let { JSONObject(it) })
        message.chatType = ChatType.ChatRoom
        message.body = customBody
        message.to = ChatroomUIKitClient.getInstance().getContext().getCurrentRoomInfo().roomId
        message.setMessageStatusCallback(object : ChatCallback{
            override fun onSuccess() {
                onSuccess.invoke(message)
                ChatroomUIKitClient.getInstance().callbackEvent(ChatroomResultEvent.SEND_MESSAGE, ChatError.EM_NO_ERROR, "")
            }

            override fun onError(code: Int, error: String?) {
                onError.invoke(code,error)
                ChatroomUIKitClient.getInstance().callbackEvent(ChatroomResultEvent.SEND_MESSAGE, code, error)
            }
        })
        chatManager.sendMessage(message)
    }
}
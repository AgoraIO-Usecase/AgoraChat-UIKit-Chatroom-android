package io.agora.chat.uikit.chatroom.viewmodel.broadcast

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import io.agora.chat.uikit.chatroom.service.ChatMessage
import io.agora.chat.uikit.chatroom.service.ChatTextMessageBody
import io.agora.chat.uikit.chatroom.service.ChatroomChangeListener

class GlobalBroadcastViewModel(
    val content:MutableList<String> = mutableListOf(),
    val duration:Long = 3000,          //End stay time
    val durationMillis:Int = 4500,    //Animation duration
    val service: io.agora.chat.uikit.chatroom.UIChatroomService
): ViewModel(), ChatroomChangeListener {
    private val _marqueeTextList: MutableList<String> = content.toMutableStateList()
    val marqueeTextList: List<String> = _marqueeTextList

    fun addMarqueeText(content:String){
        _marqueeTextList.add(content)
    }

    fun removeMarqueeText(index:Int = 0){
        _marqueeTextList.removeAt(index)
    }

    /**
     * Register chatroom change listener
     */
    fun registerChatroomChangeListener() {
        service.getChatService().bindListener(this)
    }

    /**
     * Unregister chatroom change listener
     */
     fun unRegisterChatroomChangeListener() {
        service.getChatService().unbindListener(this)
    }

    override fun onCleared() {
        super.onCleared()
        unRegisterChatroomChangeListener()
    }

    override fun onBroadcastReceived(message: ChatMessage) {
        super.onBroadcastReceived(message)
        if(message.body is ChatTextMessageBody){
            addMarqueeText((message.body as ChatTextMessageBody).message)
        }
    }
}
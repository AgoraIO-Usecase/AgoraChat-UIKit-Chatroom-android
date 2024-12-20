package io.agora.chat.uikit.chatroom.http

object ChatroomHttpManager {
    init {
        HttpManager.setBaseURL(baseUrl)
    }

    /**
     * Get the ChatroomInterface.
     */
    fun getService(): ChatroomInterface {
        return HttpManager.getService(ChatroomInterface::class.java)
    }

}
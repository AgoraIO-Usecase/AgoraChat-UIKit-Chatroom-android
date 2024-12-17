package io.agora.chat.uikit.chatroom.model

import io.agora.chat.uikit.chatroom.service.UserEntity
import java.io.Serializable

class UIChatroomInfo(
    var roomId:String,
    var roomOwner:UserEntity?

):UICreateRoomInfo(), Serializable
package io.agora.chat.uikit.chatroom.service

// manager
typealias ChatClient = io.agora.chat.ChatClient
typealias ChatRoomManager = io.agora.chat.ChatRoomManager
typealias ChatUserInfoManager = io.agora.chat.UserInfoManager
typealias ChatOptions = io.agora.chat.ChatOptions

// callback
typealias ChatCallback = io.agora.CallBack
typealias ChatValueCallback<T> = io.agora.ValueCallBack<T>
typealias ChatCursorResult<T> = io.agora.chat.CursorResult<T>
typealias ChatPageResult<T> = io.agora.chat.PageResult<T>

typealias ChatException = io.agora.exceptions.ChatException
typealias ChatError =  io.agora.Error
typealias ChatLog = io.agora.util.EMLog
// java bean
typealias Chatroom =  io.agora.chat.ChatRoom
typealias ChatUserInfo = io.agora.chat.UserInfo

// ChatMessage
typealias ChatMessage = io.agora.chat.ChatMessage
typealias ChatType = io.agora.chat.ChatMessage.ChatType
typealias ChatMessageType = io.agora.chat.ChatMessage.Type
typealias ChatTextMessageBody = io.agora.chat.TextMessageBody
typealias ChatCustomMessageBody = io.agora.chat.CustomMessageBody
typealias MessagePinInfo = io.agora.chat.MessagePinInfo
typealias PinOperation = io.agora.chat.MessagePinInfo.PinOperation
typealias TextMessageBody = io.agora.chat.TextMessageBody


// Listeners
typealias ChatConnectionListener = io.agora.ConnectionListener
typealias ChatMessageListener = io.agora.MessageListener
typealias ChatRoomChangeListener = io.agora.ChatRoomChangeListener
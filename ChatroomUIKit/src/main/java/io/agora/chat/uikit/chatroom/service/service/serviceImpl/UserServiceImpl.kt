package io.agora.chat.uikit.chatroom.service.serviceImpl

import io.agora.chat.uikit.chatroom.ChatroomUIKitClient
import io.agora.chat.uikit.chatroom.model.UserInfoProtocol
import io.agora.chat.uikit.chatroom.model.toUser
import io.agora.chat.uikit.chatroom.model.transfer
import io.agora.chat.uikit.chatroom.service.CallbackImpl
import io.agora.chat.uikit.chatroom.service.ChatClient
import io.agora.chat.uikit.chatroom.service.ChatUserInfo
import io.agora.chat.uikit.chatroom.service.ChatValueCallback
import io.agora.chat.uikit.chatroom.service.OnError
import io.agora.chat.uikit.chatroom.service.OnSuccess
import io.agora.chat.uikit.chatroom.service.OnValueSuccess
import io.agora.chat.uikit.chatroom.service.UserService
import io.agora.chat.uikit.chatroom.service.UserStateChangeListener
import io.agora.chat.uikit.chatroom.service.transfer


class UserServiceImpl: UserService {
    private val listeners = mutableListOf<UserStateChangeListener>()
    private val userInfoManager by lazy { ChatClient.getInstance().userInfoManager() }
    @Synchronized
    override fun bindUserStateChangeListener(listener: UserStateChangeListener) {
        if (listeners.contains(listener)) {
            listeners.add(listener)
            ChatroomUIKitClient.getInstance().updateChatroomUserStateChangeListener(listeners)
        }
    }

    @Synchronized
    override fun unbindUserStateChangeListener(listener: UserStateChangeListener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener)
            ChatroomUIKitClient.getInstance().updateChatroomUserStateChangeListener(listeners)
        }
    }

    override fun getUserInfo(userId: String,
                             onSuccess: OnValueSuccess<UserInfoProtocol>,
                             onError: OnError
    ) {
        getUserInfoList(arrayListOf(userId), onSuccess = { onSuccess.invoke(it[0]) }, onError)
    }

    override fun getUserInfoList(
        userIdList: List<String>,
        onSuccess: OnValueSuccess<List<UserInfoProtocol>>,
        onError: OnError
    ) {
        userInfoManager.fetchUserInfoByUserId(userIdList.toTypedArray(), object :ChatValueCallback<Map<String, ChatUserInfo>> {
            override fun onSuccess(value: Map<String, ChatUserInfo>?) {
                val userEntities = value?.map {
                    it.value.transfer().transfer()
                } ?: listOf()
                onSuccess.invoke(userEntities)
            }

            override fun onError(error: Int, errorMsg: String?) {
                onError.invoke(error, errorMsg)
            }
        })
    }

    override fun updateUserInfo(
        userEntity: UserInfoProtocol,
        onSuccess: OnSuccess,
        onError: OnError
    ) {
        userInfoManager.updateOwnInfo(userEntity.transfer(), object :ChatValueCallback<String> {
            override fun onSuccess(value: String?) {
                onSuccess.invoke()
            }

            override fun onError(error: Int, errorMsg: String?) {
                onError.invoke(error, errorMsg)
            }
        })
    }

    override fun login(userId: String, token: String, onSuccess: OnSuccess, onError: OnError) {
        ChatClient.getInstance().loginWithToken(userId, token, CallbackImpl(onSuccess, onError))
    }

    override fun login(
        user: UserInfoProtocol,
        token: String,
        onSuccess: OnSuccess,
        onError: OnError
    ) {
        ChatroomUIKitClient.getInstance().getChatroomUser().setUserInfo(user.userId, user.toUser())
        ChatClient.getInstance().loginWithToken(user.userId, token, CallbackImpl(onSuccess, onError))
    }

    override fun logout(onSuccess: OnSuccess, onError: OnError) {
        ChatClient.getInstance().logout(true, CallbackImpl(onSuccess, onError))
    }
}
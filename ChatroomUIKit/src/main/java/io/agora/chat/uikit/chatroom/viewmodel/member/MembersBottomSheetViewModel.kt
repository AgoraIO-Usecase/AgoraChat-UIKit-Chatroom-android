package io.agora.chat.uikit.chatroom.viewmodel.member

import io.agora.chat.uikit.chatroom.UIChatroomService
import io.agora.chat.uikit.chatroom.viewmodel.menu.BottomSheetViewModel

data class MembersBottomSheetViewModel(
    val roomId: String,
    val roomService: UIChatroomService,
    val isAdmin: Boolean = false,
): BottomSheetViewModel<String>()
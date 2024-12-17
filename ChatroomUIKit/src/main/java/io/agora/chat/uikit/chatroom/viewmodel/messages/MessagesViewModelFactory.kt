package io.agora.chat.uikit.chatroom.viewmodel.messages

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.agora.chat.uikit.chatroom.ChatroomUIKitClient
import io.agora.chat.uikit.chatroom.UIChatroomService
import io.agora.chat.uikit.chatroom.commons.ComposeChatListController
import io.agora.chat.uikit.chatroom.commons.ComposeMessageListState
import io.agora.chat.uikit.chatroom.commons.ComposerChatBarController
import io.agora.chat.uikit.chatroom.compose.utils.parsingGift
import io.agora.chat.uikit.chatroom.model.UICapabilities
import io.agora.chat.uikit.chatroom.model.UIChatBarMenuItem
import io.agora.chat.uikit.chatroom.model.gift.AUIGiftTabInfo
import io.agora.chat.uikit.chatroom.uikit.R
import io.agora.chat.uikit.chatroom.viewmodel.UIRoomViewModel
import io.agora.chat.uikit.chatroom.viewmodel.gift.ComposeGiftListViewModel
import io.agora.chat.uikit.chatroom.viewmodel.gift.ComposeGiftSheetViewModel
import io.agora.chat.uikit.chatroom.viewmodel.menu.RoomMemberMenuViewModel

class MessagesViewModelFactory(
    private val context: Context,
    private val roomId: String,
    private val service: UIChatroomService,
    private val isDarkTheme: Boolean? = ChatroomUIKitClient.getInstance().getCurrentTheme(),
    private val showDateSeparators: Boolean = true,
    private val showLabel: Boolean = false,
    private val showAvatar: Boolean = true,
    private val emojiColumns:Int = 7,
    private val menuItemResource: List<UIChatBarMenuItem> = listOf(
        UIChatBarMenuItem(R.drawable.icon_bottom_bar_gift, 0)
    ),
    private val giftTabInfo: List<AUIGiftTabInfo> = parsingGift(context)
) : ViewModelProvider.Factory{

    /**
     * The list of factories that can build [ViewModel]s that our Messages feature components use.
     */
    private val factories: Map<Class<*>, () -> ViewModel> = mapOf(
        MessageChatBarViewModel::class.java to {
            MessageChatBarViewModel(
                isDarkTheme = isDarkTheme,
                menuItemResource = menuItemResource,
                emojiColumns = emojiColumns,
                composerChatBarController = ComposerChatBarController(
                    context = context,
                    roomId = service.getRoomInfo().roomId,
                    chatService = service.getChatService(),
                    capabilities = setOf(UICapabilities.SEND_MESSAGE)
                )
            )
        },
        MessageListViewModel::class.java to {
            MessageListViewModel(
                isDarkTheme = isDarkTheme,
                showDateSeparators = showDateSeparators,
                showLabel = showLabel,
                showAvatar = showAvatar,
                roomId = roomId,
                chatService = service,
                composeChatListController = ComposeChatListController(
                    roomId = service.getRoomInfo().roomId,
                    messageState = ComposeMessageListState(),
                )

            )
        },
        ComposeGiftSheetViewModel::class.java to {
            ComposeGiftSheetViewModel(
                giftTabInfo = giftTabInfo
            )
        },

        UIRoomViewModel::class.java to {
            UIRoomViewModel(
                service = service,
                isDarkTheme = isDarkTheme,
            )
        },

        ComposeGiftListViewModel::class.java to {
            ComposeGiftListViewModel(
                service = service,
            )
        },

        RoomMemberMenuViewModel::class.java to {
            RoomMemberMenuViewModel(
                isDarkTheme = ChatroomUIKitClient.getInstance().getCurrentTheme(),
                title = "",
                menuList = emptyList(),
                isShowTitle = true,
                isShowCancel = true,
            )
        },
    )

    /**
     * Creates the required [ViewModel] for our use case, based on the [factories] we provided.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel: ViewModel = factories[modelClass]?.invoke()
            ?: throw IllegalArgumentException(
                "MessagesViewModelFactory can only create instances of " +
                        "the following classes: ${factories.keys.joinToString { it.simpleName }}"
            )

        @Suppress("UNCHECKED_CAST")
        return viewModel as T
    }
}
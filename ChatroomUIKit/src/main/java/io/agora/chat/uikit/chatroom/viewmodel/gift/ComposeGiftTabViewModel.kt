package io.agora.chat.uikit.chatroom.viewmodel.gift

import io.agora.chat.uikit.chatroom.model.gift.AUIGiftTabInfo
import io.agora.chat.uikit.chatroom.viewmodel.tab.TabWithVpViewModel

class ComposeGiftTabViewModel(
    giftTabInfo:List<AUIGiftTabInfo>,
): TabWithVpViewModel<AUIGiftTabInfo>(contentList = giftTabInfo) {

}
package io.agora.chat.uikit.chatroom.viewmodel.gift

import io.agora.chat.uikit.chatroom.model.gift.AUIGiftTabInfo
import io.agora.chat.uikit.chatroom.viewmodel.menu.BottomSheetViewModel

class ComposeGiftSheetViewModel(
    isDarkTheme: Boolean? = false,
    isShowTitle:Boolean = false,
    isShowCancel:Boolean = false,
    title:String = "",
    giftTabInfo:List<AUIGiftTabInfo>,
    isExpanded: Boolean = false,
) : BottomSheetViewModel<AUIGiftTabInfo>(
    isDarkTheme, isShowTitle, isShowCancel, title,
    isExpanded = isExpanded,
    contentList = giftTabInfo
){

}
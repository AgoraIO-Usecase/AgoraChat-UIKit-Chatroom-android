package io.agora.chat.uikit.chatroom.compose.bottomtoolbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.agora.chat.uikit.chatroom.commons.ComposerInputMessageState
import io.agora.chat.uikit.chatroom.viewmodel.messages.MessageChatBarViewModel
import io.agora.chat.uikit.chatroom.widget.WidgetInputField

/**
 * Input field for the Messages/Conversation screen. Allows label customization, as well as handlers
 * when the input changes.
 *
 * @param composerMessageState The state of the input.
 * @param onValueChange Handler when the value changes.
 * @param modifier Modifier for styling.
 * @param maxLines The number of lines that are allowed in the input.
 */
@Composable
fun ComposeMessageInput(
    composerMessageState: ComposerInputMessageState,
    onValueChange: (String) -> Unit,
    onKeyDown:(String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MessageChatBarViewModel,
    maxLines: Int = DefaultMessageInputMaxLines,
) {
    val (value) = composerMessageState

    WidgetInputField(
        modifier = modifier,
        maxLines = maxLines,
        onValueChange = onValueChange,
        enabled = true,
        viewModel = viewModel,
        onKeyDown = onKeyDown,
    )
}

/**
 * The default number of lines allowed in the input. The message input will become scrollable after
 * this threshold is exceeded.
 */
private const val DefaultMessageInputMaxLines = 6

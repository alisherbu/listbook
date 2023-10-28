package kaa.alisherbu.feature.player.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kaa.alisherbu.feature.player.store.PlayerState
import kaa.alisherbu.listbook.chapter.component.ChapterComponent
import kotlinx.coroutines.flow.StateFlow

interface PlayerComponent {
    val state: StateFlow<PlayerState>
    val dialogSlot: Value<ChildSlot<*, ChildDialog>>
    fun onBackClicked()

    fun onPreviousAudio()
    fun onPlayPauseAudio()
    fun onNextAudio()
    fun onUserPositionChange(value: Long)
    fun onUserPositionChangeFinished()
    fun onDownloadClick()
    fun onRemoveClick()
    fun onChapterClick()
    fun onChapterDismiss()
    sealed interface Output {
        data object Back : Output
    }

    sealed interface ChildDialog {
        class ChapterDialog(val component: ChapterComponent) : ChildDialog
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
        ): PlayerComponent
    }
}

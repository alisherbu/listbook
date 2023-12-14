package kaa.alisherbu.feature.player.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.feature.player.component.PlayerComponent.ChildDialog
import kaa.alisherbu.feature.player.component.PlayerComponent.Output
import kaa.alisherbu.feature.player.store.Intent
import kaa.alisherbu.feature.player.store.PlayerState
import kaa.alisherbu.feature.player.store.PlayerStore
import kaa.alisherbu.listbook.chapter.component.ChapterComponent
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize
import javax.inject.Provider

@Suppress("LongParameterList", "TooManyFunctions")
class PlayerComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
    @Assisted private val audioBook: AudioBook,
    private val chapterFactory: ChapterComponent.Factory,
    private val storeProvider: Provider<PlayerStore>,
) : PlayerComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    override val state: StateFlow<PlayerState> = store.stateFlow

    init {
        store.accept(Intent.UpdateAudioBook(audioBook))
    }

    private val dialogNavigation = SlotNavigation<DialogConfig>()

    override val dialogSlot: Value<ChildSlot<*, ChildDialog>> = childSlot(
        source = dialogNavigation,
        handleBackButton = true,
        childFactory = ::createChildDialog,
    )

    override fun onBackClicked() {
        output(Output.Back)
    }

    override fun onPreviousAudio() {
        store.accept(Intent.SkipToPreviousAudio)
    }

    override fun onPlayPauseAudio() {
        store.accept(Intent.PlayOrPause)
    }

    override fun onNextAudio() {
        store.accept(Intent.SkipToNextAudio)
    }

    override fun onUserPositionChange(value: Long) {
        store.accept(Intent.ChangeUserPosition(value))
    }

    override fun onUserPositionChangeFinished() {
        store.accept(Intent.ChangeUserPositionFinished)
    }

    override fun onDownloadClick() {
        store.accept(Intent.Download)
    }

    override fun onRemoveClick() {
        store.accept(Intent.Remove)
    }

    override fun onChapterDismiss() {
        dialogNavigation.dismiss()
    }

    override fun onChapterClick() {
        dialogNavigation.activate(DialogConfig.ChapterDialog(audioBook))
    }

    private fun createChildDialog(
        config: DialogConfig,
        componentContext: ComponentContext
    ): ChildDialog = when (config) {
        is DialogConfig.ChapterDialog -> {
            ChildDialog.ChapterDialog(
                chapterFactory(
                    componentContext,
                    config.audioBook,
                    ::onChapterOutput
                )
            )
        }
    }

    private fun onChapterOutput(output: ChapterComponent.Output) {
        when (output) {
            is ChapterComponent.Output.Play -> {
                store.accept(Intent.Play(output.chapter))
                dialogNavigation.dismiss()
            }
        }
    }

    private sealed interface DialogConfig : Parcelable {
        @Parcelize
        class ChapterDialog(val audioBook: AudioBook) : DialogConfig
    }

    @AssistedFactory
    interface Factory : PlayerComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            audioBook: AudioBook,
            output: (Output) -> Unit,
        ): PlayerComponentImpl
    }
}

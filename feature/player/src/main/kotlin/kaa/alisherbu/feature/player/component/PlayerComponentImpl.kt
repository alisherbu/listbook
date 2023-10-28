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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize
import javax.inject.Provider

class PlayerComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
    private val chapterFactory: ChapterComponent.Factory,
    private val storeProvider: Provider<PlayerStore>,
) : PlayerComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    override val state: StateFlow<PlayerState> = store.stateFlow

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
        dialogNavigation.activate(DialogConfig.ChapterDialog)
    }

    private fun createChildDialog(
        config: DialogConfig,
        componentContext: ComponentContext
    ): ChildDialog = when (config) {
        DialogConfig.ChapterDialog -> {
            ChildDialog.ChapterDialog(chapterFactory(componentContext))
        }
    }

    private sealed interface DialogConfig : Parcelable {
        @Parcelize
        data object ChapterDialog : DialogConfig
    }

    @AssistedFactory
    interface Factory : PlayerComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
        ): PlayerComponentImpl
    }
}

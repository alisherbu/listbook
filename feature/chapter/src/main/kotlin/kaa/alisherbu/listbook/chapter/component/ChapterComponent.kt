package kaa.alisherbu.listbook.chapter.component

import com.arkivanov.decompose.ComponentContext

interface ChapterComponent {

    interface Factory {
        operator fun invoke(componentContext: ComponentContext): ChapterComponent
    }
}
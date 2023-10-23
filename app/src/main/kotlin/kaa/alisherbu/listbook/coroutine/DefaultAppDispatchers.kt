package kaa.alisherbu.listbook.coroutine

import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultAppDispatchers : AppDispatchers {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main.immediate
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}
package kaa.alisherbu.listbook.core.util

/**
 * A common callback for listening for values
 */
interface ValueCallback<in T> {

    /**
     * Delivers values to the host (typically an [Observer])
     */
    fun onNext(value: T)
}
package kaa.alisherbu.listbook.core.util

/**
 * Convenience method for [ValueCallback.onNext]
 */
operator fun <T> ValueCallback<T>.invoke(value: T) {
    onNext(value)
}

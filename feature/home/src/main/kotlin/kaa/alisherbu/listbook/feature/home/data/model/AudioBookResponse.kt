package kaa.alisherbu.listbook.feature.home.data.model

import com.google.firebase.firestore.PropertyName

internal class AudioBookResponse(
    @PropertyName("id")
    val id: String? = null,
    @PropertyName("name")
    val name: String? = null
) {
}
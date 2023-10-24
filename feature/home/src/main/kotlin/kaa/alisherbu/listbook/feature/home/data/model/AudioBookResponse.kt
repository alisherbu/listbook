package kaa.alisherbu.listbook.feature.home.data.model

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName

@Keep
internal class AudioBookResponse(
    @PropertyName("id")
    val id: String? = null,
    @PropertyName("name")
    val name: String? = null,
    @PropertyName("audioUrl")
    val audioUrl: String? = null
)
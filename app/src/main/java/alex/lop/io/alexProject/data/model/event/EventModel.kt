package alex.lop.io.alexProject.data.model.event

import alex.lop.io.alexProject.data.model.ThumbnailModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventModel(
    @SerializedName("id")
    val int : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("thumbnail")
val thumbnailModel: ThumbnailModel
) : Serializable

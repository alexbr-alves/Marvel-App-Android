package alex.lop.io.alexProject.data.model.timeline

import alex.lop.io.alexProject.data.model.ThumbnailModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

data class TimelineModel(
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("modified")
    val modified: Date,
    @SerializedName("thumbnail")
    val thumbnailModel: ThumbnailModel
) : Serializable
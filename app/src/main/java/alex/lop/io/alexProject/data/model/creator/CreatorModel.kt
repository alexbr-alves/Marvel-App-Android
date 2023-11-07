package alex.lop.io.alexProject.data.model.creator

import alex.lop.io.alexProject.data.model.ThumbnailModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreatorModel(
    @SerializedName("id")
    val id:  Int,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("suffix")
    val suffix: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("thumbnail")
    val thumbnailModel: ThumbnailModel
): Serializable
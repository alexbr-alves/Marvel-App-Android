package alex.lop.io.alexProject.data.model.creator

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreatorModelResponse(
    @SerializedName("data")
    val data: CreatorModelData
) : Serializable

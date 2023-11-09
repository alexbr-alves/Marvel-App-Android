package alex.lop.io.alexProject.data.model.stories

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoriesModelResponse(
    @SerializedName("data")
    val data: StoriesModelData
) : Serializable

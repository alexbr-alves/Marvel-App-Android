package alex.lop.io.alexProject.data.model.stories

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoriesModelData(
    @SerializedName("results")
    val result: List<StoriesModel>
) : Serializable

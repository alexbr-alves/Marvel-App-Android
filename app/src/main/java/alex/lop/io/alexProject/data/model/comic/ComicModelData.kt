package alex.lop.io.alexProject.data.model.comic

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicModelData(
    @SerializedName("results")
    val resul: List<ComicModel>
) : Serializable

package alex.lop.io.alexProject.data.model.comic

import alex.lop.io.alexProject.data.model.comicsCharacter.ComicsCharacterModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicModelData(
    @SerializedName("results")
    val result: List<ComicModel>
) : Serializable

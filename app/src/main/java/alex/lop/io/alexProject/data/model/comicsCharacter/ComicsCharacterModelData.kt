package alex.lop.io.alexProject.data.model.comicsCharacter

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicsCharacterModelData(
    @SerializedName("results")
    val result: List<ComicsCharacterModel>
) : Serializable

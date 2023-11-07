package alex.lop.io.alexProject.data.model.comicsCharacter

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicsCharacterModelResponse(
    @SerializedName("data")
    val data: ComicsCharacterModelData
) : Serializable

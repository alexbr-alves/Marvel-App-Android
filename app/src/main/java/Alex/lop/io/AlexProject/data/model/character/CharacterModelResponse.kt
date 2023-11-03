package Alex.lop.io.AlexProject.data.model.character

import com.google.gson.annotations.SerializedName

data class CharacterModelResponse(
    @SerializedName("data")
    val data: CharacterModelData
)

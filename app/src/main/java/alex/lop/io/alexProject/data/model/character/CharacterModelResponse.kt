package alex.lop.io.alexProject.data.model.character

import com.google.gson.annotations.SerializedName

data class CharacterModelResponse(
    @SerializedName("data")
    val data : CharacterModelData
)

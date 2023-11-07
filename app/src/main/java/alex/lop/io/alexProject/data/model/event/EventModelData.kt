package alex.lop.io.alexProject.data.model.event

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventModelData(
    @SerializedName("results")
    val result: List<EventModel>
) : Serializable

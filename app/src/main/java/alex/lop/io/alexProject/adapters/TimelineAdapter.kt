package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.data.model.timeline.TimelineModel
import alex.lop.io.alexProject.databinding.LayoutCardNameBinding
import alex.lop.io.alexProject.databinding.LayoutTimelineBinding
import alex.lop.io.alexProject.util.Constants
import alex.lop.io.alexProject.util.limitDescription
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimelineAdapter : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    inner class TimelineViewHolder(val binding : LayoutTimelineBinding) :
        RecyclerView.ViewHolder(binding.root)
    private val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    private val data: Date? = originalFormat.parse("2023-11-16T16:07:08-0500")
    private val desireDataFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
    private val desiredTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    private val differCallback = object : DiffUtil.ItemCallback<TimelineModel>() {
        override fun areItemsTheSame(oldItem : TimelineModel, newItem : TimelineModel) : Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem : TimelineModel,
            newItem : TimelineModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.description == newItem.description &&
                    oldItem.title == newItem.title &&
                    oldItem.modified == newItem.modified &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var timelineList: List<TimelineModel>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter {
            it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE
        })

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : TimelineViewHolder {
        return TimelineViewHolder(
            LayoutTimelineBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() : Int {
        return timelineList.size
    }

    override fun onBindViewHolder(holder : TimelineViewHolder, position : Int) {
        val timeline = timelineList[position]
        holder.binding.apply {
            name.text = timeline.title.limitDescription(30)
            textData.text =
                "${desiredTimeFormat.format(timeline.modified)} - ${desireDataFormat.format(timeline.modified)}"
            if (timeline.description.isNullOrEmpty()) {
                description.setGone()
            } else {
                description.text = timeline.description.limitDescription(80)
            }
            loadImage(
                image,
                timeline.thumbnailModel.path,
                timeline.thumbnailModel.extension
            )

            holder.itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(timeline)
                }
            } }
    }

    private var onItemClickListener : ((TimelineModel) -> Unit)? = null

    fun setOnClickListener(listener : (TimelineModel) -> Unit) {
        onItemClickListener = listener
    }
}
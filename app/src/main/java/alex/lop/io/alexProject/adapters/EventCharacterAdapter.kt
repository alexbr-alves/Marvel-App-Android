package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.data.model.event.EventModel
import alex.lop.io.alexProject.databinding.LayoutMiniCardsBinding
import alex.lop.io.alexProject.util.limitDescription
import alex.lop.io.alexProject.util.loadImage
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class EventCharacterAdapter : RecyclerView.Adapter<EventCharacterAdapter.EventViewHolder>()  {

    inner class EventViewHolder(val binding : LayoutMiniCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<EventModel>() {

        override fun areItemsTheSame(oldItem : EventModel, newItem : EventModel) : Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem : EventModel,
            newItem : EventModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var events : List<EventModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : EventViewHolder {
        return EventViewHolder(
            LayoutMiniCardsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder : EventViewHolder, position : Int) {
        val event = events[position]
        holder.binding.apply {
            textName.text = event.title
            if (!event.description.isNullOrEmpty()) {
                textDescription.text = event.description.limitDescription(50)
            }

            loadImage(
                image,
                event.thumbnailModel.path,
                event.thumbnailModel.extension
            )
        }

    }
}
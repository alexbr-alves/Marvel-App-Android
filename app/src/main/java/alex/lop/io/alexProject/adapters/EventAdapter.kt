package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.data.model.event.EventModel
import alex.lop.io.alexProject.databinding.ItemEventBinding
import alex.lop.io.alexProject.util.loadImage
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(val binding : ItemEventBinding) :
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
                    oldItem.description == newItem.description &&
                    oldItem.title == newItem.title &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var eventList: List<EventModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : EventViewHolder {
        return EventViewHolder(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() : Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder : EventViewHolder, position : Int) {
        val event = eventList[position]
        holder.binding.apply {
            tvNameEvent.text = event.title
            loadImage(
                imgEvent,
                event.thumbnailModel.path,
                event.thumbnailModel.extension
            )
        }
    }
}